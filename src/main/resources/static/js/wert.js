$(document).ready(function() {
    $(".productCategory").change(function (e) {
        $("#abc").closest("form").submit();
    })

    var total = 0;
    $('.sum').change(function() {
        var productSize = $(this).closest('tr').find('.input-number').val();

        if($(this).is(':checked'))
        {
            total += $(this).data('price') * productSize;
            $(this).closest('tr').find('button,input.input-number').attr('disabled','disabled');
            if(total > 0)
                $("#give-order").removeAttr("disabled");
        }else {
            $(this).closest('tr').find('button,input.input-number').removeAttr('disabled');
            total -= $(this).data('price');
            if(total == 0)
                $("#give-order").attr("disabled","disabled");
        }
        $("#total").val(total);
    });
});




$('.btn-number').click(function(e){
    e.preventDefault();

    var fieldName = $(this).attr('data-field');
    var  type     = $(this).attr('data-type');
    var input = $(this).closest('.input-group').find("input[name='"+fieldName+"']");
    var currentVal = parseInt(input.val());
    if (!isNaN(currentVal)) {
        if(type == 'minus') {
            if(currentVal > input.attr('min')) {
                input.val(currentVal - 1).change();
            }
            if(parseInt(input.val()) == input.attr('min')) {
                $(this).attr('disabled', true);
            }
        } else if(type == 'plus') {
            if(currentVal < input.attr('max')) {
                input.val(currentVal + 1).change();
            }
            if(parseInt(input.val()) == input.attr('max')) {
                $(this).attr('disabled', true);
            }
        }
    } else {
        input.val(0);
    }
});
$('.input-number').focusin(function(){
    $(this).data('oldValue', $(this).val());
});
$('.input-number').change(function() {
    var elemMin = $(this).closest('.input-group').find(".btn-number[data-type='minus'][data-field='"+name+"']");
    var elemPlus = $(this).closest('.input-group').find(".btn-number[data-type='plus'][data-field='"+name+"']");
    minValue =  parseInt($(this).attr('min'));
    maxValue =  parseInt($(this).attr('max'));
    valueCurrent = parseInt($(this).val());

    name = $(this).attr('name');
    if(valueCurrent >= minValue) {
        elemMin.removeAttr('disabled')
    } else {
        alert('Sorry, the minimum value was reached');
        $(this).val($(this).data('oldValue'));
    }
    if(valueCurrent <= maxValue) {
        elemPlus.removeAttr('disabled')
    } else {
        alert('Sorry, the maximum value was reached');
        $(this).val($(this).data('oldValue'));
    }


});
$(".input-number").keydown(function (e) {
    // Allow: backspace, delete, tab, escape, enter and .
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
        // Allow: Ctrl+A
        (e.keyCode == 65 && e.ctrlKey === true) ||
        // Allow: home, end, left, right
        (e.keyCode >= 35 && e.keyCode <= 39)) {
        // let it happen, don't do anything
        return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }
});


