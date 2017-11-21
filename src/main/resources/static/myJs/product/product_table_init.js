var lang = {
    "sProcessing": "处理中...",
    "sLengthMenu": "每页 _MENU_ 项",
    "sZeroRecords": "没有匹配结果",
    "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
    "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
    "sInfoPostFix": "",
    "sSearch": "搜索:",
    "sUrl": "",
    "sEmptyTable": "表中数据为空",
    "sLoadingRecords": "载入中...",
    "sInfoThousands": ",",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页",
        "sJump": "跳转"
    },
    "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
    }
};
var productTableInitParams = {

    "bProcessing": true,
    "bAutoWidth": false,
    "oLanguage": lang,
    "sAjaxSource": "/products/class/0",
    "aoColumns": [
        {"mDataProp": "id", "bVisible": false, "sClass": "center"},
        {"mDataProp": "number", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "name", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "color", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "size", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "unitPrice", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "piecesPerBox", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "description", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "typeId", "sClass": "center", "bVisible": false},
        {"mDataProp": "userId", "sClass": "center", "bVisible": false}
    ],
    "aaSorting": [[1, "asc"]]
};

var productStocksTableInitParams = {

    "bProcessing": true,
    "bAutoWidth": false,
    "oLanguage": lang,
    "sAjaxSource": "/productStocks/class/0",
    "aoColumns": [
        {"mDataProp": "product.id", "sClass": "center", "bVisible": false},
        {
            "mDataProp": "product.imagePath", "sClass": "center", "bVisible": false,
            "mRender": function (data, type, full) {
                return "<img style='height: 50%; width: 50%' src='productImages/" + data + "'/>";
            }
        },
        {"mDataProp": "product.number", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "product.name", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "product.color", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "product.size", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "product.unitPrice", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "product.piecesPerBox", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "product.description", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "product.typeId", "sClass": "center", "bVisible": false},
        {"mDataProp": "product.userId", "sClass": "center", "bVisible": false},
        {"mDataProp": "stock.id", "sClass": "center", "bVisible": false},
        {"mDataProp": "stock.position", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "stock.countOfPieces", "sClass": "center", "sDefaultContent": "0"},
        {"mDataProp": "", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {
            "mDataProp": "", "sClass": "center", "sDefaultContent": "无", "bSortable": false,
            "mRender": function (data, type, full) {
                return "<a class='edit_product btn btn-primary btn-sm' href='#productUpdateModal' data-toggle='modal'> <i class='fa fa-edit'></i></a> &nbsp;" +
                    "<a class='delete_product btn btn-danger btn-sm' href='#productDeleteModal' data-toggle='modal'> <i class='fa fa-trash-o'></i></a>";
            }
        }
    ],
    "aaSorting": [[1, "asc"]],
    "fnRowCallback": function (nRow, aaData, iDisplayIndex, iDisplayIndexOfAadata) {
        var piecesPerBox = aaData.product.piecesPerBox;
        var count = aaData.stock.countOfPieces;
        var boxes = Math.floor(count / piecesPerBox);
        var pie = count % piecesPerBox;
        var content;
        if (pie == 0) {
            content = count + "&nbsp;<span class='label label-success'>" + boxes + "箱整" + "</span>"; // (" + boxes + "箱整)";
        } else {
            content = count + "&nbsp;<span class='label label-success'>" + boxes + "箱零" + pie + "片" + "</span>";
        }
        $('td:eq(8)', nRow).html(content); //计算箱和片数

        var unitPrice = aaData.product.unitPrice;
        var totalMoney = unitPrice * count;
        $('td:eq(9)', nRow).html(totalMoney); //计算值多少钱
        //添加编码图片popover
        //var img = "<img style='width: auto; height: auto; max-width: 100%; max-height: 100%;' src='productImages/" + aaData.product.imagePath + "'/>";
        //var popover = "<span class='btn btn-info popovers' data-container=\"body\" data-trigger='hover' data-html='true' data-placement='right' " +
        //    "data-content=\""+ img +"\" data-original-title=\""+ aaData.product.number +"\">"+ aaData.product.number + "</>";
        var popover = "<button class='btn btn-info btn-sm layui-image-button'>" + aaData.product.number + "</button>";
        $('td:eq(0)', nRow).html(popover);
        return nRow;
    },
    "fnInitComplete": function (oSettings, json) {
        // $('.popovers').popover(); //手动添加的在添加完毕后要再初始化
    }
};

var productTable;

function refreshData() {
    var productClassId = $('#product-class-selected-node-id').val();
    if (productClassId == '') {
        productClassId = 0;
    }
    productTableInitParams.sAjaxSource = "/products/class/" + productClassId;
    productTable.fnDestroy();

    productStocksTableInitParams.sAjaxSource = "/productStocks/class/" + productClassId;

    productTable = $("#product-table").dataTable(productStocksTableInitParams);
}


function initProductTable() {

    var productClassId = $('#product-class-selected-node-id').val();
    if (productClassId == '') {
        productClassId = 0;
    }
    productTableInitParams.sAjaxSource = "/products/class/" + productClassId;
    productStocksTableInitParams.sAjaxSource = "/productStocks/class/" + productClassId;
    var table = $('#product-table').dataTable(productStocksTableInitParams);
    return table;
}

function productImageUpdate2Preview(imagePath) {
    $('#productImageUpdate2').removeClass("fileupload-new").addClass("fileupload-exists");
    var preview = "<img style='max-height: 150px;' src='productImages/" + imagePath + "'/>";
    $('#productImageUpdate2').find("div.fileupload-preview").html(preview);
}

function productImageUpdate2PreviewESC() {
    $('#productImageUpdate2').removeClass("fileupload-exists").addClass("fileupload-new");
    $('#productImageUpdate2').find("div.fileupload-preview").html("");
}

$(document).ready(function () {
    productTable = initProductTable();
    //删除
    $(document).on('click', '.delete_product', function () {
        var nTr = $(this).parents('tr')[0];
        var data = productTable.fnGetData(nTr);
        $('#delete-modal-title-span').html(data.product.number);
        $('#product_id3').val(data.product.id);
    });
    //编辑
    $(document).on('click', '.edit_product', function () {
        var nTr = $(this).parents('tr')[0];
        var data = productTable.fnGetData(nTr);
        $('#update-modal-title-span').html(data.product.number);
        $('#product_number2').val(data.product.number).attr("readonly", true);
        if (data.product.name != "无") {
            $('#product_name2').val(data.product.name);
        }
        if (data.product.color != "无") {
            $('#product_color2').val(data.product.color);
        }
        if (data.product.size != "无") {
            $('#product_size2').val(data.product.size);
        }
        $('#product_unitPrice2').val(data.product.unitPrice).attr("readonly",true);

        $('#product_piecesPerBox2').val(data.product.piecesPerBox).attr("readonly", true);
        if (data.stock.position != "无") {
            $('#stock_position2').val(data.stock.position);
        }
        if (data.stock.countOfPieces != "0") {
            $('#stock_countOfPieces2').val(data.stock.countOfPieces);
            $('#stock_countOfPieces2').attr('readonly', true);
        }
        //console.log(data);
        if (data.product.description != "无") {
            $('#product_description2').val(data.product.description);
        }
        if (data.product.imagePath != "") {
            productImageUpdate2Preview(data.product.imagePath);
        }
        $('#product_id2').val(data.product.id);
        $('#stock_id2').val(data.stock.id);
        //console.log(data);
    });

    //点击编号出现图片
    $(document).on('click', '.layui-image-button', function () {
        var nTr = $(this).parents('tr')[0];
        var data = productTable.fnGetData(nTr);
        var img;
        if (data.product.imagePath == "") {
            img = "<img style='width: auto; height: auto; max-width: 100%; max-height: 100%;' src='http://www.placehold.it/400x400/EFEFEF/AAAAAA&amp;text=no+image'/>";
        } else {
            img = "<img style='width: auto; height: auto; max-width: 100%; max-height: 100%;' src='productImages/" + data.product.imagePath + "'/>";
        }
        layer.open({
            type: 1,
            title: data.product.number,
            closeBtn: 0,
            offset: '100px',
            area: 'auto',
            //skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content: img
        });
    });
});