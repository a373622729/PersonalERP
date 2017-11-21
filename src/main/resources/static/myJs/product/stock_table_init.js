function fnFormatDetails ( oTable, nTr )
{
    var aData = oTable.fnGetData(nTr);
    var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    sOut += '<tr><td>Rendering engine:</td><td>'+aData[1]+' '+aData[4]+'</td></tr>';
    sOut += '<tr><td>Link to source:</td><td>Could provide a link here</td></tr>';
    sOut += '<tr><td>Extra info:</td><td>And any further details here (images etc)</td></tr>';
    sOut += '</table>';

    return sOut;
}

function fnInsertDetailColumn() {
    /*
     * Insert a 'details' column to the table
     */
    var nCloneTh = document.createElement( 'th' );
    var nCloneTd = document.createElement( 'td' );
    nCloneTd.innerHTML = '<img src="images/details_open.png"/>';
    nCloneTd.className = "center";
    nCloneTd.style.width = "20px";

    $('#hidden-table-info thead tr').each( function () {
        this.insertBefore( nCloneTh, this.childNodes[0] );
    } );

    $('#hidden-table-info tbody tr').each( function () {
        this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
    } );
}

$(document).ready(function() {

    $('#dynamic-table').dataTable( {
        "sAjaxSource": "/products/class/0",
        "aoColumns":[
            { "mDataProp": "id" },
            { "mDataProp": "number" },
            { "mDataProp": "name" },
            { "mDataProp": "color" },
            { "mDataProp": "size" },
            { "mDataProp": "unitPrice" },
            { "mDataProp": "piecesPerBox" },
            { "mDataProp": "typeId" },
            { "mDataProp": "userId" }
        ],
        "aaSorting": [[ 1, "desc" ]]
    } );

    /*
     * Initialse DataTables, with no sorting on the 'details' column
     *
     */


    var oTable = $('#hidden-table-info').dataTable( {
        "sAjaxSource": "/products/class/0",
        "aoColumns":[
            { "mDataProp": "id" },
            { "mDataProp": "number" },
            { "mDataProp": "name" },
            { "mDataProp": "color" },
            { "mDataProp": "size" },
            { "mDataProp": "unitPrice" },
            { "mDataProp": "piecesPerBox" },
            { "mDataProp": "typeId" },
            { "mDataProp": "userId" }
        ],
        "aoColumnDefs": [
            { "bSortable": false, "aTargets": [ 0 ] }
        ],
        "aaSorting": [[1, 'asc']],
        "fnServerData": function (sUrl, aoData, fnCallback) {
            $.ajax({
                "url": sUrl,
                "data": aoData,
                "type": 'get',
                "success": fnCallback,
                "dataType": "json",
                "cache": false
            });
        },
        "fnInitComplete": function() {
            fnInsertDetailColumn();
        }

    });



    /* Add event listener for opening and closing details
     * Note that the indicator for showing which row is open is not controlled by DataTables,
     * rather it is done here
     */
    $(document).on('click','#hidden-table-info tbody td img',function () {
        var nTr = $(this).parents('tr')[0];
        if ( oTable.fnIsOpen(nTr) )
        {
            /* This row is already open - close it */
            this.src = "images/details_open.png";
            oTable.fnClose( nTr );
        }
        else
        {
            /* Open this row */
            this.src = "images/details_close.png";
            oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
        }
    } );
} );