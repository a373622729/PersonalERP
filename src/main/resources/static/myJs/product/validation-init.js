/**
 * Created by ios on 17/11/2.
 */
var ProductClassValidate = function () {
    return {
        init: function(){
            $(".modal-close").click(function(){
                $("#productClassInsertForm").resetForm();
            });
            $("close").click(function(){
                $("#productClassInsertForm").resetForm();
            });

            $("#productClassInsertForm").validate();

            $("#productClassInsertForm").submit(function(){
                var pid = $('#product-class-selected-node-id').val();
                var name = $('#productClassNameInsert').val();
                $(this).ajaxSubmit({
                    type: 'post',
                    //dataType:'json',
                    url: 'products/class/' + pid,
                    data: {
                        name : name
                    },
                    success: function(data) {
                        $("#productClassModalInsert").modal('hide');
                        TreeView.init();
                    },
                    error: function() {
                        alert("新增数据失败!");
                        $("#productClassModalInsert").modal('hide');
                    }
                });
                $(this).resetForm();
                return false;
            });
        }
    };

}();