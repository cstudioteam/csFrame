
/*DnDボックスとdb_modelを紐付ける
paper.on('cell:pointerup', function(cellView, evt, x, y) {
  var elementBelow = graph.get('cells').find(function(cell) {
    if (cell instanceof joint.dia.Link) return false;
    if (cell.id === cellView.model.id) return false;
    if (cell.getBBox().containsPoint(g.point(x, y))) {
      return true;
    }
    return false;
  });
  if (elementBelow&&elementBelow.get('id')==db_status.dnd.dropzone.get('id')) {
  //重ねた部分の特定部分をthis.target.dataに書き出す。
  //名前部分の変数の呼び出しが汚い。改善を検討中。
  db_status.AddData(cellView,[cellView.model.id,cellView.model.get('.status'),cellView.model.get('.status_name')]);
}});*/

//イベントハンドラ
//モーダル
//ファイル読み込みモーダル
$('#file_json').on('change',function(evt){
  var input=evt.target.files[0];
  var reader = new FileReader();
  reader.onload = (function(e) {
    var loaded=JSON.parse(e.target.result);
    //初期化
    $('#view_status').off();
    $('#view_action').off();
    $('#view_status tbody').empty();
    $('#view_action tbody').empty();
    db_status.data=loaded.wf_status_master;
    db_rote.data=loaded.fw_wf_rote;
    graph.clear();
    graph.fromJSON(loaded.graph);
  });
  reader.readAsText(input,'UTF-8');
});
//上メニュー
//statusノード生成
$('.head_btn.add_box').on('click', function () {
  var num=Object.keys(db_status.data).length;
  var datum='S'+num;
  graph.addCell(new joint.shapes.status.Element({
    '.status':datum,
    '.status_name':'',
    position: { x: 50+num*25, y: 50+num*25},
    size: { width: 150, height: 100 }
  }));
});
//roteリンク生成
$('.head_btn.add_link').on('click', function () {
  var num=Object.keys(db_rote.data).length;
  var datum='R'+num;
  graph.addCell(new joint.shapes.rote({
    source: { x: 300+num*10, y: 100+num*10},
    target: { x: 450+num*10, y: 70+num*10},
    attrs: {
      '.connection': {
        strokeWidth: 5,
        stroke: '#34495E'
      },
      '.marker-target': { fill: '#34495E', d: 'M 10 0 L 0 5 L 10 10 z' }
    },
    '.action_code':datum,
    '.action':''
  }));
});

//SQL生成ボタン
$('.head_btn.tosql').on('click',function(){
  var output='';
  output+=sql.insert(db_status.table,db_status.column,db_status.data);
  output+=sql.insert(db_rote.table,db_rote.column,db_rote.data);
  //console.log(output);
  var blob = new Blob([ output ], { "type" : "text/plain" });
  window.URL = window.URL || window.webkitURL;
  $(".head_btn.tosql").attr("href", window.URL.createObjectURL(blob));
  var link = document.createElement( 'a' );
  link.href = window.URL.createObjectURL(blob);
  link.download = 'sql_ddl';
  link.click();
});
//セーブボタン
$('.head_btn.save_json').on('click',function(){
  var output='';
  output+='{"wf_status_master":';
  output+=JSON.stringify(db_status.data);
  output+=',"fw_wf_rote":';
  output+=JSON.stringify(db_rote.data);
  output+=',"graph":'
  output+=JSON.stringify(graph.toJSON());
  output+='}'
  //console.log(output);
  var blob = new Blob([ output ], { "type" : "text/plain" });
  window.URL = window.URL || window.webkitURL;
  $(".head_btn.save_json").attr("href", window.URL.createObjectURL(blob));
  var link = document.createElement( 'a' );
  link.href = window.URL.createObjectURL(blob);
  link.download = 'wfe.json';
  link.click();
});
//ロードボタン
$('.head_btn.load_json').on('click',function(){
  $('.modal').modal('show');
});
//下メニュー(DBメニュー)
//タブボタン(.pull_btn)を押すと、cssにより表示する部分が出てくる。
$('.pull .pull_btn').on('click', function () {
  if($(this).hasClass('selected')){
    $('.pull .pull_btn.selected').removeClass('selected');
    $('#belowbar').removeClass('open');
    //graph.removeCells(db_status.dnd.dropzone);
  }
  else{
    var index = $('.pull span').index(this);
    $('.pull .pull_btn.selected').removeClass('selected');
    //graph.removeCells(db_status.dnd.dropzone);
    $(this).addClass('selected');
    $('#belowbar').addClass('open');
    $('#belowbar .content_area .contents').css('display','none');
    $('#belowbar .content_area .contents').eq(index).css('display','block');
    //if(index==0){
      //DnDできる箱を表示
      //graph.addCell(db_status.dnd.dropzone);
    //}
  }
});
