$formProduct.submit(
    function(ev){
        key=$.trim($keywords.val());
    }
    if(key.length>6&&(/^http:\/\//i.test(key)||/^https:\/\//i.test(key)||/taobao\.com/i.test(key)||/tmall\.com/i.test(key))){
        showErr(sErrNotlink);
        return false;
    }else{
        searchFunction();
    }
function searchFunction(){
        if(key==''){
            key=$keywords.attr('data-searchKey');
        }
}