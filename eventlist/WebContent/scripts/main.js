/* Date Utility functions */
function formatDate(date) {
    var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

function formatStartDate(startDate) {
    return formatDate(startDate);
}

function getEndDate(startDate, period) {
    switch (period) {
        case "week":
            return startDate.addWeeks(1);
        case "month":
            return startDate.addMonths(1);
        case "quarter":
            return startDate.addMonths(3);
        default:
            return null;
    }
}

function getPrevStartDate(startDate, period) {
    switch (period) {
        case "week":
            return startDate.addWeeks(-1);
        case "month":
            return startDate.addMonths(-1);
        case "quarter":
            return startDate.addMonths(-3);
        default:
            return null;
    }
}

function getNextStartDate(startDate, period) {
    switch (period) {
        case "week":
            return startDate.addWeeks(1);
        case "month":
            return startDate.addMonths(1);
        case "quarter":
            return startDate.addMonths(3);
        default:
            return null;
    }
}

function formatEndDate(startDate, period) {
    return formatDate(getEndDate(startDate, period).addDays(-1));
}

/* Set up UI when page is ready */
$(document).ready(function () {
    var picker = new Pikaday({field: document.getElementById('datepicker'),
        defaultDate: new Date(2015, 11, 1),
        disableDayFn: function(date){
            var period = $('input[name=period-type]:checked', '#period-type-group').val();
            if(period === 'week'){
                // Only enable Sunday
                return date.getDay() !== 0;
            }else {
                return date.getDate() !== 1;
            }
        },
        setDefaultDate: true,
        onSelect: function (date) {
            refreshDateRange();
            $('#datepicker').parent().addClass('is-dirty');
            $("#jsGrid").jsGrid("loadData");
        }});

    function refreshDateRange() {
        var date = picker.getDate();
        var period = $('input[name=period-type]:checked', '#period-type-group').val();
        $('#startDate').html(formatStartDate(date, period));
        $('#endDate').html(formatEndDate(date, period));
    }

    var db = {
        loadData: function(filter) {
            var startDate = formatDate(picker.getDate());
            var period = $('input[name=period-type]:checked', '#period-type-group').val();
            return $.ajax({
                type: "GET",
                url: "api/v1/events",
                data:  {date: startDate, period: period, filter: filter}
            });
        },

        insertItem: function(item) {
            return $.noop();
        },

        updateItem: function(item) {
            return $.noop();
        },

        deleteItem: function(item) {
            return $.ajax({
                type: "DELETE",
                url: "api/v1/events?eventId="+item.eventId
            })
            .done(function( data, textStatus, jqXHR ) {
                var notification = document.querySelector('.mdl-js-snackbar');
                notification.MaterialSnackbar.showSnackbar({ message: data   }    );
            })
            .fail(function( jqXHR, textStatus, errorThrown ) {
                var notification = document.querySelector('.mdl-js-snackbar');
                notification.MaterialSnackbar.showSnackbar({ message: textStatus   }    );
            });
        },
    };

    $("#jsGrid").jsGrid({
        width: "100%",
        height: "400px",
        sorting: true,
        paging: true,
        pageLoading: true,
        controller: db,
        deleteConfirm: function(item) {
                return "The event \"" + item.eventSummary + "\" will be removed. Are you sure?";
            },
            rowClick: function(args) {
                showDetailsDialog("Edit", args.item);
            },
        fields: [
            {title: "Id", name: "eventId", type: "number", visible: false},
            {title: "Date", name: "eventDate", type: "text", width: 150, validate: "required"},
            {title: "Type", name: "eventType", type: "text", width: 50},
            {title: "Summary", name: "eventSummary", type: "text", width: 200},
            {title: "Metric", name: "eventMetric", type: "number"},
            {type: "control",
                modeSwitchButton: false,
                editButton: false,
                headerTemplate: function() {
                    return $("<button>").attr("type", "button").text("Add")
                        .on("click", function () {
                            showDetailsDialog("Add", {});
                        });
                }
            }
        ]
    });


    $('#prev-date').click(function (e) {
        var date = picker.getDate();
        var period = $('input[name=period-type]:checked', '#period-type-group').val();
        picker.setDate(getPrevStartDate(date, period));
        refreshDateRange();
    });

    $('#next-date').click(function (e) {
        var date = picker.getDate();
        var period = $('input[name=period-type]:checked', '#period-type-group').val();
        picker.setDate(getNextStartDate(date, period));
        refreshDateRange();
    });

    $('input[name=period-type]', '#period-type-group').change(function (e) {
        var date = picker.getDate();
        if ($(this).val() !== 'week') {
            date = date.moveToFirstDayOfMonth();
        } else {
            date.moveToNthOccurrence(0, 1);
        }
        picker.setDate(date);
        
        refreshDateRange();
        $("#jsGrid").jsGrid("loadData");
    });

    var dialog = document.querySelector('#dialog');
    if (!dialog.showModal) {
        dialogPolyfill.registerDialog(dialog);
    }
    dialog.querySelector('.close').addEventListener('click', function () {
        dialog.close();
    });
    function setSelectionRange(input, selectionStart, selectionEnd) {
        if (input.setSelectionRange) {
            input.focus();
            input.setSelectionRange(selectionStart, selectionEnd);
        } else if (input.createTextRange) {
            var range = input.createTextRange();
            range.collapse(true);
            range.moveEnd('character', selectionEnd);
            range.moveStart('character', selectionStart);
            range.select();
        }
    }

    function setCaretToPos(input, pos) {
        setSelectionRange(input, pos, pos);
    }

    function showDetailsDialog(dialogType, event) {
        if(dialogType === "Edit") {
            
            $("#dialog-event-id").val(event.eventId);
            $("#dialog-event-date").val(event.eventDate).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');
            $("#dialog-event-type").val(event.eventType).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');
            $("#dialog-event-summary").val(event.eventSummary).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');
            $("#dialog-event-metric").val(event.eventMetric).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');

            $.get("api/v1/events?eventId=" + event.eventId, null, function (data) {
                $("#modal-content").val(data.eventDetails);
                setCaretToPos($("#modal-content")[0], 1);
            });
        } else {
            $("#dialog-event-id").val("");
            $("#dialog-event-date").val("");
            $("#dialog-event-type").val("");
            $("#dialog-event-summary").val("");
            $("#dialog-event-metric").val("");
            $("#modal-content").val("");
        }
        dialog.showModal();
    }

    $("#btnSave").click(function(evt){
        var eventId = $("#dialog-event-id").val();
        var postData = {};
        postData['eventDate'] = $("#dialog-event-date").val();
        postData['eventType'] = $("#dialog-event-type").val();
        postData['eventSummary'] = $("#dialog-event-summary").val();
        postData['eventMetric'] = $("#dialog-event-metric").val();
        postData['eventDetails'] = $("#modal-content").val();

        $.post("api/v1/events" + (eventId == null ? "":"?eventId=" + eventId), postData, function (data) {

            var notification = document.querySelector('.mdl-js-snackbar');
            notification.MaterialSnackbar.showSnackbar({ message: data   }    );
            $("#jsGrid").jsGrid("loadData");
            dialog.close();
        });
    });
    
    refreshDateRange();
});