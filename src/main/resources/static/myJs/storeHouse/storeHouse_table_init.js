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

//自定义价值列的排序规则
jQuery.fn.dataTableExt.afnSortData['money-text'] = function  ( oSettings, iColumn )
{
    var aData = [];
    var nodeLength = oSettings.oApi._fnGetTrNodes(oSettings).length;
    if(nodeLength == 0) return aData;
    for (var i = 0 ; i < nodeLength; i++) {
        var unitPrice = oSettings.oApi._fnGetCellData(oSettings,i,6);
        var count = oSettings.oApi._fnGetCellData(oSettings, i, 13);
        aData.push(unitPrice * count);
    }
    //console.log(aData);
    return aData;
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
        {"mDataProp": "product.color", "sClass": "center", "sDefaultContent": "无", "bSortable": false, "bVisible": false},
        {"mDataProp": "product.size", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "product.unitPrice", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "product.piecesPerBox", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "product.description", "sClass": "center", "sDefaultContent": "无", "bSortable": false, "bVisible": false},
        {"mDataProp": "product.typeId", "sClass": "center", "bVisible": false},
        {"mDataProp": "product.userId", "sClass": "center", "bVisible": false},
        {"mDataProp": "stock.id", "sClass": "center", "bVisible": false},
        {"mDataProp": "stock.position", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "stock.countOfPieces", "sClass": "center", "sDefaultContent": "0"},
        {"mDataProp": "", "sClass": "center", "sDefaultContent": "无", "bSortable": true, "sSortDataType": "money-text"}, //计算出来的价值
        {
            "mDataProp": "", "sClass": "center", "sDefaultContent": "无", "bSortable": false,
            "mRender": function (data, type, full) {
                return '<img src="images/details_open.png">';
            }
        }
    ],
    "aaSorting": [[1, "asc"]],
    "fnRowCallback": function (nRow, aaData, iDisplayIndex, iDisplayIndexOfAadata) {
        $(nRow).addClass('gradeA');
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
        $('td:eq(6)', nRow).html(content); //计算箱和片数

        var unitPrice = aaData.product.unitPrice;
        var totalMoney = unitPrice * count;
        $('td:eq(7)', nRow).html(totalMoney); //计算值多少钱
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
    },
    "fnFooterCallback": function( nFoot, aData, iStart, iEnd, aiDisplay ) {
        if (aData.length == 0) return;
        var total = aData.reduce(function(a,b){
            return a + b.product.unitPrice * b.stock.countOfPieces;
        },0);

        var foot = "<th colspan='7' style='text-align: right'>总计</th><th colspan='2'>"+total+"元</th> ";
        $(nFoot).html(foot);
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


    /* Add event listener for opening and closing details
     * Note that the indicator for showing which row is open is not controlled by DataTables,
     * rather it is done here
     */
    $(document).on('click','#product-table tbody td img',function () {
        var nTr = $(this).parents('tr')[0];
        if ( productTable.fnIsOpen(nTr) )
        {
            /* This row is already open - close it */
            this.src = "images/details_open.png";
            productTable.fnClose( nTr );
        }
        else
        {
            /* Open this row */
            this.src = "images/details_close.png";
            productTable.fnOpen( nTr, fnFormatDetails(productTable, nTr), 'details' );
        }
    } );
});

function fnFormatDetails ( oTable, nTr )
{
    var aData = oTable.fnGetData( nTr );
    console.log(aData);
    var sOut = '<table  cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    sOut += '<tr><td>颜色:</td><td>'+aData.product.color+'</td></tr>';
    sOut += '<tr><td>描述:</td><td>'+ aData.product.description +'</td></tr>';
    var date = new Date(aData.product.createAt);
    date = dateFtt('yyyy-MM-dd hh:mm:ss', date);
    sOut += '<tr><td>添加时间:</td><td>'+ date +'</td></tr>';
    if (aData.product.updateAt != null) {
        date = dateFtt('yyyy-MM-dd hh:mm:ss', new Date(aData.product.updateAt));
        sOut += '<tr><td>修改时间:</td><td>'+ date +'</td></tr>';
    }
    sOut += '</table>';

    return sOut;
}