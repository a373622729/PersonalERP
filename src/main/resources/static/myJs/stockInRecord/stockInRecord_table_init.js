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

//自定义价值列的排序规则
jQuery.fn.dataTableExt.afnSortData['money-text'] = function  ( oSettings, iColumn )
{
    var aData = [];
    var nodeLength = oSettings.oApi._fnGetTrNodes(oSettings).length;
    if(nodeLength == 0) return aData;
    for (var i = 0 ; i < nodeLength; i++) {
        var unitPrice = oSettings.oApi._fnGetCellData(oSettings,i,3);
        var count = oSettings.oApi._fnGetCellData(oSettings, i, 4);
        aData.push(unitPrice * count);
    }
    //console.log(aData);
    return aData;
};

var stockInRecordTableInitParams = {

    "bProcessing": true,
    "bAutoWidth": false,
    "oLanguage": lang,
    "sAjaxSource": "/stockInRecord/all",
    "aoColumns": [
        {"mDataProp": "number", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "createAt", "sClass": "center", "sDefaultContent": "无", "bSortable": true,
            "mRender": function (data, type, full) {
                var date = new Date(data);
                return dateFtt('yyyy-MM-dd hh:mm:ss', date);
            }
        },
        {"mDataProp": "position", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "unitPrice", "sClass": "center", "sDefaultContent": "无"},
        {"mDataProp": "count", "sClass": "center", "sDefaultContent": "无", "bSortable": false},
        {"mDataProp": "", "sClass": "center", "sDefaultContent": "无", "bSortable": true, "sSortDataType": "money-text"} //计算出来的价值
    ],
    "aaSorting": [[1, "desc"]],
    "fnRowCallback": function (nRow, aaData, iDisplayIndex, iDisplayIndexOfAadata) {
        $(nRow).addClass('gradeA');
        var piecesPerBox = aaData.piecesPerBox;
        var count = aaData.count;
        var boxes = Math.floor(count / piecesPerBox);
        var pie = count % piecesPerBox;
        var content;
        if (pie == 0) {
            content = count + "&nbsp;<span class='label label-success'>" + boxes + "箱整" + "</span>"; // (" + boxes + "箱整)";
        } else {
            content = count + "&nbsp;<span class='label label-success'>" + boxes + "箱零" + pie + "片" + "</span>";
        }
        $('td:eq(4)', nRow).html(content); //计算箱和片数

        var unitPrice = aaData.unitPrice;
        var totalMoney = unitPrice * count;
        $('td:eq(5)', nRow).html(totalMoney); //计算值多少钱
        //添加编码图片popover
        var popover = "<button class='btn btn-info btn-sm layui-image-button'>" + aaData.number + "</button>";
        $('td:eq(0)', nRow).html(popover);
        return nRow;
    },
    "fnInitComplete": function (oSettings, json) {
        // $('.popovers').popover(); //手动添加的在添加完毕后要再初始化
    },
    "fnFooterCallback": function( nFoot, aData, iStart, iEnd, aiDisplay ) {
        if (aData.length == 0) return;
        var total = aData.reduce(function(a,b){
            return a + b.unitPrice * b.count;
        },0);

        var foot = "<th colspan='5' style='text-align: right'>总计</th><th style='text-align: center'><p class='text-primary'>"+total+"元</p></th> ";
        $(nFoot).html(foot);
    }
};


function submitSpinning() {
    var submitBtn = $('button[type=submit]');
    submitBtn.attr('disabled',true);
    $('.submit_spinner').addClass('fa fa-spinner fa-spin');
}

function removeSpinning( ) {
    var submitBtn = $('button[type=submit]');
    submitBtn.attr('disabled',false);
    $('.submit_spinner').removeClass('fa fa-spinner fa-spin');
}

var stockInRecordTable;

function refreshData() {
    submitSpinning();
    var start = $('#startDate').val();
    var end = $('#endDate').val();

    stockInRecordTable.fnDestroy();
    stockInRecordTableInitParams.sAjaxSource = "/stockInRecord/all?s="+ start + "&e=" + end;
    stockInRecordTable = $("#record-table").dataTable(stockInRecordTableInitParams);
    removeSpinning();
}


function initTable() {

    stockInRecordTableInitParams.sAjaxSource = "/stockInRecord/all";
    var table = $('#record-table').dataTable(stockInRecordTableInitParams);

    return table;
}

$(document).ready(function() {
    stockInRecordTable = initTable();

    //点击编号出现图片
    $(document).on('click', '.layui-image-button', function () {
        var nTr = $(this).parents('tr')[0];
        var data = stockInRecordTable.fnGetData(nTr);
        var img;
        if (data.imagePath == "") {
            img = "<img style='width: auto; height: auto; max-width: 100%; max-height: 100%;' src='http://www.placehold.it/400x400/EFEFEF/AAAAAA&amp;text=no+image'/>";
        } else {
            img = "<img style='width: auto; height: auto; max-width: 100%; max-height: 100%;' src='productImages/" + data.imagePath + "'/>";
        }
        layer.open({
            type: 1,
            title: data.number,
            closeBtn: 0,
            offset: '100px',
            area: 'auto',
            //skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content: img
        });
    });
});
