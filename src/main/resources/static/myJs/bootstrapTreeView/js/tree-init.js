/**
 * Created by ios on 17/11/1.
 */
var TreeView = function () {

    return {
        //main function to initiate the module
        init: function () {

            //var tree = [
            //    {
            //        text: "Parent 1",
            //        nodes: [
            //            {
            //                text: "Child 1",
            //                nodes: [
            //                    {
            //                        text: "Grandchild 1"
            //                    },
            //                    {
            //                        text: "Grandchild 2"
            //                    }
            //                ]
            //            },
            //            {
            //                text: "Child 2"
            //            }
            //        ]
            //    },
            //    {
            //        text: "Parent 2"
            //    },
            //    {
            //        text: "Parent 3"
            //    },
            //    {
            //        text: "Parent 4"
            //    },
            //    {
            //        text: "Parent 5"
            //    }
            //];
            var tree = [{text: "所有"}];
            var $treeView = $('#bootstrapTree');
            $.ajax({
                type: "get",
                dataType: "json",
                url:"/products/class",
                success: function(data) {
                    $treeView.treeview({
                        data: data,
                        showBorder: false,
                        collapseIcon: "fa fa-folder-open-o",
                        expandIcon: "fa fa-folder-o",
                        emptyIcon: "fa fa-leaf",
                        onNodeSelected: function(event, node){
                            $('.productClassOp').removeClass('disabled');
                            $('.modal-title-span').html(node.text);
                            $('#product-class-selected-node-id').val(node.id);
                        },
                        onNodeUnselected: function(event, node) {
                            $('.productClassOp').addClass('disabled');
                        }
                    });
                    $treeView.treeview('toggleNodeSelected',[0])
                },
                error: function() {
                    $treeView.treeview({
                        data: tree,
                        showBorder: false,
                        collapseIcon: "fa fa-folder-open-o",
                        expandIcon: "fa fa-folder-o",
                        emptyIcon: "fa fa-leaf",
                        onNodeSelected: function(event, node){
                            //alert(node.id +", " + node.text)
                        }
                    });
                    $treeView.treeview('toggleNodeSelected',[0])
                }
            });

        }
    };
}();