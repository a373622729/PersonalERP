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
            });
            $("close").click(function(){
                $("#productClassInsertForm").resetForm();
                $("#productClassUpdateForm").resetForm();
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
                        var treeNodeId = $(".node-selected").attr("data-nodeid"); //树形列表中的id
                        TreeView.init(treeNodeId);
                    },
                    error: function() {
                        alert("新增数据失败!");
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
                    type: 'put',
                    url: 'products/class/' + id,
                    data: { name: name},
                    success: function(data) {
                        $("#productClassModalUpdate").modal('hide');
                        var treeNodeId = $(".node-selected").attr("data-nodeid"); //树形列表中的id
                        TreeView.init(treeNodeId);
                    },
                    error: function() {
                        alert("数据更新失败!");
                        $("#productClassModalUpdate").modal('hide');
                    }
                });
                $(this).resetForm();
                return false;
            });

            //Delete

            $('#productClassDeleteBtn').click(function() {
                var id = $('#product-class-selected-node-id').val();
                $.ajax({
                    type:'delete',
                    url: 'products/class/' + id,
                    data:{},
                    success: function(data) {
                        if (data.indexOf("false") != -1){
                            alert("该类别或其子类别下存在商品,不可以删除!");
                        }
                        $("#productClassModalDelete").modal('hide');
                        TreeView.init();
                    },
                    error: function() {
                        alert("数据删除失败!");
                        $("#productClassModalDelete").modal('hide');
                    }
                });
            });

        }
    };

}();