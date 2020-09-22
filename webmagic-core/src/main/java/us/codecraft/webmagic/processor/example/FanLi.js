function searchFunction(){
    if(key==''){
        key=$keywords.attr('data-searchKey');
    }
    $realKeywordInput.val(key);setTimeout(function(){
        if(!isShowingDialog){
        $keywords.val('');
    }
        hideErr();},500);
}

$formBtn=$('#J_tbsearch_btn'),$errTip=$formProduct.find('.J_tbsearch_errtip'),$errTipText=$errTip.find('span');function searchInit(){$realKeywordInput.attr("name","keywords");$keywords.removeAttr("name");}
searchInit();bindSearchOverlay();var sErrNotlink='亲，淘宝/天猫升级为搜索宝贝标题拿返利喔！~请复制宝贝标题进行搜索。';var hasKeywords=/keywords\=/ig.test(window.location.href);function bindSearchOverlay(){if(!("header-tbs8pop".getCookie()>0)){$.getJSON("//fun.fanli.com/topheader/ajaxCheckHeaderPop?jsoncallback=?",function(res){if(res.status==1){$tbOverlay=$tbTipsPop.overlay({top:'20%',api:true,onLoad:function(){isShowingDialog=true;},onClose:function(){isShowingDialog=false;}});$('#btn-confirm-tb-tips').on("click",function(ev){ev.preventDefault();if($("#confirm-tb-nopop").prop("checked")){$.getJSON("//fun.fanli.com/topheader/ajaxSetHeaderPop?jsoncallback=?");}
    searchFlag=true;$tbOverlay.close();$formProduct.submit();});$tbTipsPop.find('.close').click(function(ev){ev.preventDefault();searchFlag=true;$tbOverlay.close();$formProduct.submit();});}});}}
whiteEvent();if(hasKeywords){showErr(sErrNotlink);}
function whiteEvent(){var key;var submitHandle=function(){$formProduct.submit();};$formProduct.submit(function(ev){key=$.trim($keywords.val());if(key.length>6&&(/^http:\/\//i.test(key)||/^https:\/\//i.test(key)||/taobao\.com/i.test(key)||/tmall\.com/i.test(key))){showErr(sErrNotlink);return false;}
else{searchFunction();}
    UBT.track("taobaofl_home","pc","pty-go11111~std-25999");if(!("header-tbs8pop".getCookie()>0)&&!searchFlag){$tbOverlay.load();return false;}
    else{searchFlag=false;}});function searchFunction(){if(key==''){key=$keywords.attr('data-searchKey');}
    $realKeywordInput.val(key);setTimeout(function(){if(!isShowingDialog){$keywords.val('');}
        hideErr();},500);}
    $keywords.on('keyup paste',function(){hideErr();var keyword=$.trim($keywords.val());if(keyword.length>6&&(/^http:\/\//i.test(keyword)||/^https:\/\//i.test(keyword)||/taobao\.com/i.test(keyword)||/tmall\.com/i.test(keyword))){showErr(sErrNotlink);}});$formBtn.on('click.submit',submitHandle);}
function showErr(txt){$errTipText.html(txt);$errTip.show();}
function hideErr(){$errTip.hide();$(titleTipsSelector).hide();}
$keywords.placeholder({type:'label',klass:'tb-search-placeholder',repair:true,color:'#d3d2d2',offset:[0,8]});$keywords.each(function(i){$(this).inputCleanup({id:"J_cleanup"+i,offset:{left:28,top:2},afterBuild:function($cleanup){$cleanup.removeAttr("style").wrap('<span class="srh-close"></span>')},afterCleanup:function(){hideErr();}});});$keywords.on('input.autocomplete',function(){$(this).trigger('keydown.autocomplete');}).autocomplete('https://suggest.taobao.com/sug?code=utf-8&area=b2c',{max:10,minChars:1,matchSubset:1,matchContains:1,cacheLength:0,scrollHeight:350,width:($keywords.width()+3),dataType:'jsonp',parse:function(json){var painVal=$.trim($keywords.val());if(/(taobao|tmall){1}\.com/ig.test(painVal)){$('.ac_results').hide();return[];}
        if(!json.result)
            return false;json=json.result;var data=new Array();$.each(json,function(i,n){data[i]={'name':n[0],'count':n[1]};});var parsed=[];for(var i=0;i<data.length;i++){parsed[parsed.length]={data:data[i],value:data[i].name,result:data[i].name};}
        return parsed;},formatItem:function(item){return"<div><span style=\"float:left;\">"+item.name+"</span><span style=\"float:right;\">共找到宝贝数量"+item.count+"个</span></div>";},formatMatch:function(item){return item.name;},formatResult:function(item){return item.name;}});}).initb();})(jQuery);;(function($){var $rightFa=$('#J_taobao_right_fa');if($rightFa.length>0){$rightFa.sidebar({min:0,relative:true,relativeWidth:1020});}
    (function(MODULE){MODULE.add("TimeStamp",function(options){var settings=$.extend(true,{callback:function(timestamp){}},options);$.getJSON('//fun{0}/topheader/getTime?jsoncallback=?'.format(Fanli.Utility.rootDomain),function(result){if(result.status==1){settings.callback(result.data.timestamp);}else{settings.callback(parseInt(new Date().getTime()/1000));}});});MODULE.add("ScrollLoad",function(options){var $window=$(window);var eventNamespace='.LOADMORECONTENT';var eventName="scroll{0} resize{0}".format(eventNamespace);if(typeof options=="string"&&options=="destroy"){$window.off(eventName);return;}
        var settings=$.extend(true,{$wrap:$(document),cache:true,cacheTime:5,ajaxUrl:"",postData:{},threshold:$window.height()/2,container:window,autoRenderFirst:true,successCallback:$.noop,emptyCallback:$.noop,errorCallback:$.noop,ajaxTagSelector:".J_load_more_content_tag",ajaxLoadingSelector:".J_load_more_content_loading"},options);var isEnd=false,isSending=false,loadTime=1;var $wrap=settings.$wrap;var ajaxUrl=settings.ajaxUrl;var $ajaxTag=$wrap.find(settings.ajaxTagSelector);var $loading=$wrap.find(settings.ajaxLoadingSelector);function bindScrollEvent(){$window.on(eventName,function(){if(isEnd){breakScrollLoad();return;}
            if(!isSending&&!$.belowthefold($ajaxTag,settings)){$loading.show();getContentAjax();}});}
        function getContentAjax(){isSending=true;showLoadingBox();sendAjax();}
        function sendAjax(){$.ajax({type:"GET",url:ajaxUrl,data:$.extend(true,{"page":loadTime},settings.postData),cache:true,dataType:"jsonp",jsonp:"jsoncallback",jsonpCallback:"jQuery83088605_"+parseInt(new Date().getTime()/(settings.cacheTime*60000)),success:function(result){if(result.status==1){isSending=false;settings.successCallback(result,loadTime);loadTime++;}else{breakScrollLoad();settings.errorCallback(result,loadTime);}}});}
        function renderFirstCont(){if(settings.autoRenderFirst){loadTime=1;getContentAjax();}}
        function showLoadingBox(){$loading.show();}
        function hideLoadingBox(){$loading.hide();}
        function breakScrollLoad(){$loading&&$loading.hide();isSending=false;$window.off(eventName);}
        function setup(){renderFirstCont();bindScrollEvent();}
        setup();});MODULE.add("AjaxList",function(){var $tab=$(".J_zdm_tab");var $tabNext=$(".J_zdm_tab_next");if($tab.length==0&&$tabNext.length==0){return;}
        var $contentWrap=$("#J_content_wrap");var $loadInner=$contentWrap.find(".J_load_content_inner");var $window=$(window);var $tabNextDivs=$tabNext.find("div");var $empty=$(".J_loading_empty");function bindLazyload(){$("img.lazy").lazyload({threshold:$window.height()*3,effect:"show"});}
        function initAjax(){$tabNext.on("click","a.J_zdm_tab_next_ys",function(){var $this=$(this);var cid=$this.data("id");var sid=$tab.find("a").eq(0).data("id");var ajaxUrl="//huodong.fanli.com/Hongbao20171111/getArtProducts?source=2&tab={0}&type={1}&from_sc=1&jsoncallback=?".format(sid,cid);var type=1;$this.addClass("current").siblings().removeClass("current");$loadInner.html("");MODULE.ScrollLoad("destroy");initChoiceness(ajaxUrl,type);});$tabNext.on("click","a.J_zdm_tab_next_zdm",function(){var $this=$(this);var cid=$this.data("id");var sid=$tab.find("a").eq(1).data("id")||2;var ajaxUrl="//huodong.fanli.com/Hongbao20171111/getArtProducts?source=2&tab={0}&type={1}&from_sc=1&jsoncallback=?".format(sid,cid);var type=2;$this.addClass("current").siblings().removeClass("current");$loadInner.html("");MODULE.ScrollLoad("destroy");initChoiceness(ajaxUrl,type);});$tab.on("click","a",function(){var $this=$(this);var index=$this.index();var $parent=$this.parent();var id=$this.data("id");$this.addClass("current").siblings().removeClass("current");$tabNextDivs.hide();if(id!=3){$tabNext.show();$tabNextDivs.eq(index).show();$tabNextDivs.eq(index).find("a").eq(0).trigger("click");}else{$tabNext.hide();$tabNextDivs.eq(0).find("a").eq(0).trigger("click");}});if($tab.length!=0){$tab.find("a.current").trigger("click");}else{$tabNextDivs.eq(1).show().find("a").eq(0).trigger("click");}}
        function initChoiceness(ajaxUrl,type){bindScrollLoad({ajaxUrl:ajaxUrl,type:type});}
        function bindScrollLoad(options){var settings=$.extend(true,{ajaxUrl:"",type:1},options);var ajaxUrl=settings.ajaxUrl;$(".J_loading_box").show();MODULE.ScrollLoad({ajaxUrl:ajaxUrl,successCallback:function(result,loadtimes){if(result.data.list&&result.data.list.length>0){$empty.hide();renderHtml(result,settings.type);if(result.data.list.length<8&&loadtimes==1){$(".J_loading_box").hide();MODULE.ScrollLoad("destroy");}}else{if(loadtimes==1){$empty.show();}
                $(".J_loading_box").hide();MODULE.ScrollLoad("destroy");}}});}
        function renderHtml(result,type){var data=result.data;if(type==1){var tpl=document.getElementById("J_item_tpl_ys").innerHTML;}else if(type==2){var tpl=document.getElementById("J_item_tpl_yg").innerHTML;}
            var html=juicer(tpl,data);$loadInner.append(html);bindLazyload();setTimeout(function(){UBT.PlugIns.Exposure.init();bindCountdown();},200);}
        function bindCountdown(){var $itemCount=$(".J_item_cd_time");if($itemCount.length!=0||$tab.length==0){MODULE.TimeStamp({callback:function(timestamp){var endtime=$itemCount.data("time");if(endtime>timestamp){$loadInner.addClass("no");$itemCount.countdown({until:Number(endtime)-timestamp,format:'HMS',layout:'<em>{hnn}</em>:<em>{mnn}</em>:<em>{snn}</em>',onExpiry:function(){$loadInner.removeClass("no").addClass("begin");}});}else{$loadInner.addClass("begin");}}});}}
        function setup(){initAjax();}
        setup();}).AjaxList();})(FLNS.register("Fanli.Shop.Hongbao20171111.Scroll"));})(jQuery);