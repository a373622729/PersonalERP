/**
 * Created by ios on 17/11/2.
 */
var hasSameNumber1 = true, hasSameNumber2 = true;
$(document).ready(function() {
    $('#product_number1').on('focus', function(){
        $('#product_number1_error').remove();
    });

    $('#product_number1').on('blur', function () {
        $.ajax({
            url: "products/number",
            type: 'get',
            data: {
                number: $('#product_number1').val()
            },
            success: function(data) {
                if (data.indexOf('true') != -1) { //有相同的
                    $('#product_number1').after("<label id='product_number1_error' for=\"product_number1\" class=\"error\">该编号已存在</label>");
                } else {
                    hasSameNumber1 = false;
                }
            }
        })
    });
});

function validateUpdateProductStock(formData, jqForm, options) {
    var validate = $("#productUpdateForm").validate();
    if (validate.errorList.length > 0) {
        return false;
    }

    //验证图片是否合法
    //var filePreview = $('#fileupload-preview').children('img');
    //console.log(filePreview);
    var queryString = $.param(formData);
    submitSpinning();
    return true;
}

function validateInsertProductStock(formData, jqForm, options) {

    var validate = $("#productInsertForm").validate();
    if (validate.errorList.length > 0) {
        return false;
    }
    //验证一下是否有相同的编号
    if(hasSameNumber1) return false;
    //验证图片是否合法
    //var filePreview = $('#fileupload-preview').children('img');
    //console.log(filePreview);
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

var ProductValidate = function () {
    return {
        init: function () {
            var insertValida = $("#productInsertForm").validate();
            var updateValida = $('#productUpdateForm').validate();
            $(".modal-close").click(function(){
                $('#productInsertForm').resetForm();
                removeSpinning(insertValida);
                $('#productUpdateForm').resetForm();
                removeSpinning(updateValida);
                productImageUpdate2PreviewESC();
            });

            $(".close").click(function(){
                $('#productInsertForm').resetForm();
                removeSpinning(insertValida);
                $('#productUpdateForm').resetForm();
                removeSpinning(updateValida);
                productImageUpdate2PreviewESC();
            });
            //$("#productInsertForm").validate();


            $("#productInsertForm").submit(function () {

                var classId = $('#product-class-selected-node-id').val();
                $(this).ajaxSubmit({
                    beforeSubmit: validateInsertProductStock,
                    //clearForm: true,
                    resetForm: true,
                    type: 'post',
                    url: 'productStocks/class/' + classId,
                    success: function (data) {
                        $('#productInsertModal').modal('hide');
                        removeSpinning();
                        var json = eval(data);
                        if (json.insertProductStock == true && json.saveImage == false) {
                            layer.msg("图片上传失败,其他的添加成功");
                        } else if (json.insertProductStock == true) {
                            layer.msg("添加成功");
                        } else if (json.insertProductStock == false) {
                            layer.msg("添加失败");
                        }
                        refreshData();
                    },
                    error: function() {
                        $('#productInsertModal').modal('hide');
                        removeSpinning();
                        layer.msg("添加失败");
                    }
                });
                return false;
            });

            $("#productUpdateForm").submit(function () {

                var productId = $('#product_id2').val();
                $(this).ajaxSubmit({
                    beforeSubmit: validateUpdateProductStock,
                    //clearForm: true,
                    resetForm: true,
                    type: 'post',
                    url: 'productStocks/product/' + productId,
                    success: function (data) {
                        $('#productUpdateModal').modal('hide');
                        removeSpinning();
                        var json = eval(data);
                        if (json.updateProductStock == true && json.saveImage == false) {
                            layer.msg("图片上传失败,其他的修改成功");
                        } else if (json.updateProductStock == true) {
                            layer.msg("修改成功");
                        } else if (json.updateProductStock == false) {
                            layer.msg("修改失败");
                        }
                        productImageUpdate2PreviewESC();
                        refreshData();
                    },
                    error: function() {
                        $('#productUpdateModal').modal('hide');
                        removeSpinning();
                        layer.msg("添加失败");
                        productImageUpdate2PreviewESC();
                    }
                });
                return false;
            });

            //删除
            $('#productDeleteBtn').click(function() {
                var id = $('#product_id3').val();
                var res= confirm("请您再次确认,真的要删除该商品信息吗?");
                submitSpinning();
                if (res == false) {
                    removeSpinning();
                    $("#productDeleteModal").modal('hide');
                    return;
                }
                $.ajax({
                    type:'delete',
                    url: 'productStocks/product/' + id,
                    data:{},
                    success: function(data) {
                        var json = eval(data);
                        if (json.success == true) {
                            refreshData();
                            layer.msg("删除成功!");
                        } else {
                            layer.msg("删除失败!");
                        }
                        $("#productDeleteModal").modal('hide');

                        removeSpinning();
                    },
                    error: function() {
                        layer.msg("删除失败!");
                        removeSpinning();
                        $("#productDeleteModal").modal('hide');
                    }
                });
            });
        }
    };

}();


