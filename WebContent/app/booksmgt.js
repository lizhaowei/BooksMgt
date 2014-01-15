Ext.require([ '*' ]);
Ext.onReady(function() {

  Ext.QuickTips.init();

  var sysTitle = "小葵花图书管理系统^_^";
  var bookDatileFormUrl = 'http://localhost:8080/BooksMgt/booksmgt';
  var win = null;
  var currentPage = 1;

  Ext.define('ForumThread', {
    extend : 'Ext.data.Model',
    fields : [ 'id', 'isbn', 'title', 'subtitle', 'bookDetail', 'author', 'authorDetail', 'specification', 'pages', 'publisher',
        { name : 'publicationDate', type : 'date', }, 'bookStatus', 'evaluation', 'avaliableToLend' ], idProperty : 'title' });

  var store = Ext.create('Ext.data.Store', {
    pageSize : 25,
    model : 'ForumThread',
    proxy : { type : 'jsonp', url : 'http://localhost:8080/BooksMgt/booksmgt',
      reader : { root : 'books', totalProperty : 'totalCount' }, simpleSortMode : true } });

  store.on('beforeload', function(store, options) {
    Ext.apply(store.proxy.extraParams, { reqType : 'getBooks' });
  });

  var tbar = Ext.create('Ext.toolbar.Toolbar', { renderTo : document.body, width : 600,
    items : [ { width : 100, text : '添加书籍', handler : function() {
      var formPanel = createFormPanel();
      win = createPopupWin('新增书籍信息', formPanel);
      win.show();
    } }, { width : 100, text : '修改书籍' }, { width : 100, text : '删除书籍' }, { width : 100, text : '导出书籍' } ] });

  var gridPanel = Ext.create('Ext.grid.Panel', {
    layout : 'fit',
    // width : 1250,
    height : '100%',
    title : sysTitle,
    store : store,
    loadMask : true,
    tbar : tbar,
    columns : [
        { id : 'id', text : '序号', hidden : true, dataIndex : 'id' },
        { text : 'ISBN', sortable : true, dataIndex : 'isbn' },
        { text : '书名', sortable : true, width : 120, dataIndex : 'title' },
        { text : '副标题', sortable : true, width : 120, dataIndex : 'subtitle' },
        { text : '丛书信息', sortable : true, width : 120, dataIndex : 'bookDetail' },
        { text : '作者', sortable : true, width : 120, dataIndex : 'author' },
        { text : '作者详情', sortable : true, width : 120, dataIndex : 'authorDetail' },
        { text : '装订规格', sortable : true, dataIndex : 'specification' },
        { text : '页数', sortable : true, dataIndex : 'pages' },
        { text : '出版社', sortable : true, width : 180, dataIndex : 'publisher' },
        { text : '出版日期', sortable : false, width : 120, renderer : Ext.util.Format.dateRenderer('Y-m-d'),
          dataIndex : 'publicationDate' }, { text : '状态', sortable : true, dataIndex : 'bookStatus' },
        { text : '评价', sortable : true, dataIndex : 'evaluation' }, { text : '备注', hidden : true, dataIndex : 'bookRemark' },
        { text : '是否可借', dataIndex : 'avaliableToLend' } ],
    bbar : Ext.create('Ext.PagingToolbar', { store : store, displayInfo : true, displayMsg : '{0}  -  {1}  /  {2}',
      emptyMsg : "......" }) });

  function createFormPanel() {
    return Ext.create('Ext.form.Panel', {
      frame : true,
      bodyStyle : 'padding:5px 5px 0',
      fieldDefaults : { msgTarget : 'side', labelWidth : 75 },
      defaultType : 'textfield',
      defaults : { anchor : '100%' },
      items : [
          { fieldLabel : 'ISBN', name : 'isbn' },
          { fieldLabel : '书名', name : 'title', allowBlank : false },
          { fieldLabel : '副标题', name : 'subtitle' },
          { fieldLabel : '丛书信息', name : 'bookDetail' },
          { fieldLabel : '作者', name : 'author' },
          { fieldLabel : '作者详情', name : 'authorDetail' },
          {
            fieldLabel : '装订规格',
            name : 'specification',
            xtype : 'combobox',
            mode : 'local',
            value : '平装',
            triggerAction : 'all',
            forceSelection : true,
            editable : false,
            displayField : 'name',
            valueField : 'value',
            queryMode : 'local',
            store : Ext.create('Ext.data.Store', { fields : [ 'name', 'value' ],
              data : [ { name : '平装', value : '平装' }, { name : '精装', value : '精装' }, { name : '其它', value : '其它' } ] }) },
          { fieldLabel : '页数', name : 'pages' },
          { fieldLabel : '出版社', name : 'publisher', allowBlank : false },
          { fieldLabel : '出版日期', xtype : 'datefield', name : 'publicationDate', maxValue : new Date(), value : new Date(),
            format : 'Y-m-d' },
          {
            fieldLabel : '状态',
            name : 'bookStatus',
            xtype : 'combobox',
            mode : 'local',
            value : '还未开始',
            triggerAction : 'all',
            forceSelection : true,
            editable : false,
            displayField : 'name',
            valueField : 'value',
            queryMode : 'local',
            store : Ext.create('Ext.data.Store', {
              fields : [ 'name', 'value' ],
              data : [ { name : '还未开始', value : '还未开始' }, { name : '阅读中', value : '阅读中' }, { name : '已读完', value : '已读完' },
                  { name : '参考书', value : '参考书' }, { name : '未读完', value : '未读完' }, { name : '已放弃', value : '已放弃' } ] }),
            allowBlank : false },
          {
            fieldLabel : '评价',
            name : 'evaluation',
            xtype : 'combobox',
            mode : 'local',
            value : '-1',
            triggerAction : 'all',
            forceSelection : true,
            editable : false,
            displayField : 'name',
            valueField : 'value',
            queryMode : 'local',
            store : Ext.create('Ext.data.Store', {
              fields : [ 'name', 'value' ],
              data : [ { name : '5', value : '5' }, { name : '4', value : '4' }, { name : '3', value : '3' },
                  { name : '2', value : '2' }, { name : '1', value : '1' }, { name : '-1', value : '-1' } ] }),
            allowBlank : false },
          { fieldLabel : '备注', xtype : 'textareafield', height : 60, name : 'bookRemark' },
          // { fieldLabel : '是否可借', name : 'avaliableToLend' },
          {
            fieldLabel : '是否可借',
            name : 'avaliableToLend',
            xtype : 'combobox',
            mode : 'local',
            value : '暂不可借',
            triggerAction : 'all',
            forceSelection : true,
            editable : false,
            displayField : 'name',
            valueField : 'value',
            queryMode : 'local',
            store : Ext.create('Ext.data.Store', { fields : [ 'name', 'value' ],
              data : [ { name : '暂不可借', value : '暂不可借' }, { name : '可借', value : '可借' } ] }), allowBlank : false },
          { xtype : 'hiddenfield', name : 'id' }, { xtype : 'hiddenfield', name : 'currentPage' },
          { xtype : 'hiddenfield', name : 'reqType', value : 'addBook' } ],

      buttons : [ { text : '添加', handler : function() {
        var bookDetailForm = this.up('form').getForm();
        if (!bookDetailForm.isValid()) {
          Ext.Msg.alert('笨笨^^', '老婆你没有填写完整啊~~~');
          return;
        }
        bookDetailForm.submit({ url : bookDatileFormUrl, success : function(form, action) {
          Ext.Msg.alert('你真厉害呀老婆^_^', action.result.msg);
          // this.up('window').hide();
          if (null != win) {
            win.hide();
          }
          store.loadPage(store.currentPage);

        }, failure : function(form, action) {
          switch (action.failureType) {
          case Ext.form.action.Action.CLIENT_INVALID:
            Ext.Msg.alert('笨笨^^', '老婆你没有填写完整啊~~~');
            break;
          case Ext.form.action.Action.CONNECT_FAILURE:
            Ext.Msg.alert('笨笨^^', '老婆，系统有问题了啊，快找你的亲亲老公~~~');
            break;
          case Ext.form.action.Action.SERVER_INVALID:
            Ext.Msg.alert('Failure', action.result.msg);
          }
        } });
      } }, { text : '取消', handler : function() {
        this.up('form').getForm().reset();
        this.up('window').hide();
      } } ] });
  }

  function createPopupWin(title, formPanel) {
    return Ext.create('widget.window', { title : title, closeAction : 'hide', width : 400, minWidth : 350, layout : 'fit',
      resizable : false, constrain : true, modal : true, y : 20, bodyStyle : 'padding: 2px;', items : formPanel });
  }

  gridPanel.on('itemdblclick', function(thiz, record, item, index, eventObject, eOpts) {
    var formPanel = createFormPanel();

    formPanel.getForm().findField('id').setValue(record.get('id'));
    formPanel.getForm().findField('isbn').setValue(record.get('isbn'));
    formPanel.getForm().findField('title').setValue(record.get('title'));
    formPanel.getForm().findField('subtitle').setValue(record.get('subtitle'));
    formPanel.getForm().findField('specification').setValue(record.get('specification'));
    formPanel.getForm().findField('pages').setValue(record.get('pages'));
    formPanel.getForm().findField('publisher').setValue(record.get('publisher'));
    formPanel.getForm().findField('publicationDate').setValue(record.get('publicationDate'));
    formPanel.getForm().findField('bookStatus').setValue(record.get('bookStatus'));
    formPanel.getForm().findField('evaluation').setValue(record.get('evaluation'));
    formPanel.getForm().findField('bookRemark').setValue(record.get('bookRemark'));
    formPanel.getForm().findField('bookDetail').setValue(record.get('bookDetail'));
    formPanel.getForm().findField('authorDetail').setValue(record.get('authorDetail'));
    formPanel.getForm().findField('avaliableToLend').setValue(record.get('avaliableToLend'));

    // win = Ext.create('widget.window', { title : '书籍信息', closeAction : 'hide', width : 400, minWidth : 350, layout : 'fit',
    // resizable : false, constrain : true, modal : true, y : 20, bodyStyle : 'padding: 2px;', items : formPanel });
    win = createPopupWin('修改书籍信息', formPanel);
    win.show();

  });

  store.loadPage(currentPage);

  Ext.create('Ext.Viewport', { layout : { type : 'border', padding : 2 },
    items : [ { region : 'center', xtype : 'panel', items : [ gridPanel ] } ] });

});
