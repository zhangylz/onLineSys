$(function () {
    $("#jqGrid").jqGrid({
        url: '../sysitem/list',
        datatype: "json",
        colModel: [			
			{ label: '题号', name: 'id', width: 50, key: true },
			{ label: '题目标题', name: 'title', width: 150},
			{ label: '分数', name: 'score', width: 25 },
            { label: '难度', name: 'level', width: 20 ,formatter:levelFmt},
			{ label: '作者', name: 'author', width: 30 },
			{ label: '创建时间', name: 'createTime', width: 50,formatter:getLocalTime },
			{ label: '是否可见', name: 'isVisible', width: 30, formatter:statusFmt}		
        ],
		viewrecords: true,
        height: '400px',
        rowNum: 25,
        rowList : [25,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

function statusFmt(v) {
	if (v == 0) {
		v = '<i class="fa fa-times" aria-hidden="true"></i>';
		//v = '前台不显示';
	} else {
		v = '<i class="fa fa-check" aria-hidden="true"></i>';
	}
	return v;
}



function levelFmt(level) {
    var levelStr = '难';
    if(level==1){
        levelStr='简单';
    }else if(level=2){
        levelStr='一般';
    }else if(level=3){
        levelStr='难';
    }else if(level=4){
        levelStr='困难';
    }
    return levelStr;
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		
	},
	methods: {
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "sysitem_edit.html?id="+id;
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sysitem/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		change : function(event){
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要更改选中记录状态？', function(){
				$.ajax({
					type: "POST",
				    url: "../sysitem/change",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		}
	}
});

function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
}
