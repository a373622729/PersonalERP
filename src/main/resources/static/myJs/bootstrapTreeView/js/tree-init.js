/**
 * Created by ios on 17/11/1.
 */
var TreeView = function () {

    return {
        //main function to initiate the module
        init: function (selectId) {
            var tree = [{text: "所有"}];
            var $treeView = $('#bootstrapTree');
            $.ajax({
                type: "get",
                dataType: "json",
                url: "/products/class",
                success: function (data) {
                    $treeView.treeview({
                        data: data,
                        showBorder: false,
                        collapseIcon: "fa fa-folder-open-o",
                        expandIcon: "fa fa-folder-o",
                        emptyIcon: "fa fa-leaf",
                        onNodeSelected: function (event, node) {
                            if (node.id == 0) {
                                $('#deleteOp').addClass('disabled');
                                $('#updateOp').addClass('disabled');
                                $('#insertOp').removeClass('disabled');
                            } else {
                                $('.productClassOp').removeClass('disabled');
                            }

                            $('.modal-title-span').html(node.text);
                            $('.product_table_span').html(node.text);
                            $('#product-class-selected-node-id').val(node.id); //数据库中类型对应的id


                            //判断是否为叶子节点, 非叶子节点不可以添加货物
                            if (node.id == 0 || node.nodes != undefined) {
                                $('#add_product_button').addClass('disabled');
                            } else {
                                $('#add_product_button').removeClass('disabled');
                            }
                            refreshData(); //更新表格数据

                        },
                        onNodeUnselected: function (event, node) {
                            $('.productClassOp').addClass('disabled');
                        }

                    });

                    if (selectId === undefined) {
                        $treeView.treeview('toggleNodeSelected', [0])
                    } else {
                        $treeView.treeview('toggleNodeSelected', [Number(selectId)])
                    }
                },
                error: function () {
                    $treeView.treeview({
                        data: tree,
                        showBorder: false,
                        collapseIcon: "fa fa-folder-open-o",
                        expandIcon: "fa fa-folder-o",
                        emptyIcon: "fa fa-leaf",
                        onNodeSelected: function (event, node) {
                            //alert(node.id +", " + node.text)
                        }
                    });
                    $treeView.treeview('toggleNodeSelected', [0])
                }
            });

        }
    };
}();