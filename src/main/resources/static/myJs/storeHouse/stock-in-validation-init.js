/**
 * Created by ios on 17/11/2.
 */

function validateStockIn(formData, jqForm, options) {
    var validate = $("#stockInForm").validate();
    if (validate.errorList.length > 0) {
        return false;
    }
    var queryString = $.param(formData);
    submitSpinning();
    return true;
}

function submitSpinning() {
    var submitBtn = $('button[type=submit]');
    submitBtn.attr('disabled',true);
    $('.submit_spinner').addClass('fa fa-spinner fa-spin');
}

function removeSpinning( valida ) {
    var submitBtn = $('button[type=submit]');
    submitBtn.attr('disabled',false);
    $('.submit_spinner').removeClass('fa fa-spinner fa-spin');

    valida != undefined ?valida.resetForm() : 0;
}

function removeTags() {
    $('#stock_position_tagsinput').find('span.tag').remove();
}

var StockInValidate = function () {
    return {
        init: function () {
            var updateValida = $('#stockInForm').validate();
            $(".modal-close").click(function(){
                $('#stockInForm').resetForm();
                removeSpinning(updateValida);
                removeTags();
            });

            $(".close").click(function(){
                $('#stockInForm').resetForm();
                removeSpinning(updateValida);
                removeTags();
            });
            //$("#productInsertForm").validate();

            $("#stockInForm").submit(function () {

                var stockId = $('#stock_id').val();
                $(this).ajaxSubmit({
                    beforeSubmit: validateStockIn,
                    //clearForm: true,
                    resetForm: true,
                    type: 'post',
                    url: 'storeHouse/stocks/' + stockId,
                    success: function (data) {
                        $('#stockInModal').modal('hide');
                        removeSpinning();
                        removeTags();
                        var json = eval(data);
                        if (json.success == true) {
                            layer.msg("入库成功");
                        } else {
                            layer.msg("入库失败");
                        }
                        refreshData();
                    },
                    error: function() {
                        $('#stockInModal').modal('hide');
                        removeSpinning();
                        removeTags();
                        layer.msg("入库失败");
                    }
                });
                return false;
            });
        }
    };

}();


