/**
 * Created by ios on 17/11/2.
 */
var ProductClassValidate = function () {
    return {
        init: function(){

            //Insert
            $(".modal-close").click(function(){
                $("#productClassInsertForm").resetForm();
                $("#productClassUpdateForm").resetForm();
                removeSpinning();
            });
            $(".close").click(function(){
                $("#productClassInsertForm").resetForm();
                $("#productClassUpdateForm").resetForm();
                removeSpinning();
            });

            $("#productClassInsertForm").validate();

            $("#productClassInsertForm").submit(function(){
                var pid = $('#product-class-selected-node-id').val();
                var name = $('#productClassNameInsert').val();
                $(this).ajaxSubmit({
                    beforeSubmit: validateInsertProductClass,
                    type: 'post',
                    //dataType:'json',
                    url: 'products/class/' + pid,
                    data: {
                        name : name
                    },
                    success: function(data) {
                        if (data.indexOf("false") != -1){
                            layer.msg("添加失败");
                            return;
                        }
                        $("#productClassModalInsert").modal('hide');
                        var treeNodeId = $(".node-selected").attr("data-nodeid"); //树形列表中的id
                        TreeView.init(treeNodeId);
                        layer.msg("添加成功!");
                        removeSpinning();
                    },
                    error: function() {
                        layer.msg("添加失败!");
                        removeSpinning();
                        $("#productClassModalInsert").modal('hide');
                    }
                });
                $(this).resetForm();
                return false;
            });

            //Update
            $("#productClassUpdateForm").validate();
            $("#productClassUpdateForm").submit(function(){
                var id = $('#product-class-selected-node-id').val();  //productId
                var name = $('#productClassNameUpdate').val();
                $(this).ajaxSubmit({
                    beforeSubmit: validateUpdateProductClass,
                    type: 'put',
                    url: 'products/class/' + id,
                    data: { name: name},
                    success: function(data) {
                        if (data.indexOf("false") != -1){
                            layer.msg("更改失败");
                            return;
                        }

                        $("#productClassModalUpdate").modal('hide');
                        var treeNodeId = $(".node-selected").attr("data-nodeid"); //树形列表中的id
                        TreeView.init(treeNodeId);
                        layer.msg("更改成功!");
                        removeSpinning();
                    },
                    error: function() {
                        removeSpinning();
                        layer.msg("更改失败!");
                        $("#productClassModalUpdate").modal('hide');
                    }
                });
                $(this).resetForm();
                return false;
            });

            //Delete

            $('#productClassDeleteBtn').click(function() {
                var id = $('#product-class-selected-node-id').val();
                submitSpinning();
                $.ajax({
                    type:'delete',
                    url: 'products/class/' + id,
                    data:{},
                    success: function(data) {
                        if (data.indexOf("false") != -1){
                            alert("该类别或其子类别下存在商品,不可以删除!");
                            return;
                        }
                        $("#productClassModalDelete").modal('hide');
                        TreeView.init();
                        layer.msg("删除成功!");
                        removeSpinning();
                    },
                    error: function() {
                        layer.msg("删除失败!");
                        removeSpinning();
                        $("#productClassModalDelete").modal('hide');
                    }
                });
            });

        }
    };

}();


function validateInsertProductClass(formData, jqForm, options) {
    var validate = $("#productClassInsertForm").validate();
    if (validate.errorList.length > 0) {
        return false;
    }
    submitSpinning();
    return true;
}

function validateUpdateProductClass(formData, jqForm, options) {
    var validate = $("#productClassUpdateForm").validate();
    if (validate.errorList.length > 0) {
        return false;
    }
    submitSpinning();
    return true;
}

