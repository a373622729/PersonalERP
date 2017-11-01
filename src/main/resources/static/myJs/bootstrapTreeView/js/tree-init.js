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
                        collapseIcon: "fa fa-minus",
                        expandIcon: "fa fa-plus",
                        emptyIcon: "fa fa-leaf"
                    });
                    $treeView.treeview('toggleNodeSelected',[0, {silent: true}])
                },
                error: function() {
                    $treeView.treeview({
                        data: tree,
                        showBorder: false,
                        collapseIcon: "fa fa-minus",
                        expandIcon: "fa fa-plus",
                        emptyIcon: "fa fa-leaf"
                    });
                    $treeView.treeview('toggleNodeSelected',[0, {silent: true}])
                }
            });

        }
    };
}();