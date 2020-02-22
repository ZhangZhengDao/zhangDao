function property(property) {
    if (property==''){
        window.location.href="/";
    }else{
        window.location.href="/?"+"property="+property;
    }
}