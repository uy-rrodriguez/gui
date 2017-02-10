!(function(doc, src, timeout){
    var f = doc.createElement('iframe');
    f.style.width='1px'; f.style.height='1px'; f.style.display='none';
    doc.body.appendChild(f);

    var fw = f.contentWindow || iframe.contentDocument;
    var to = setTimeout(function(){
        f.onload = null;
        if (fw.stop) fw.stop();
        else if(fw.execCommand) fw.execCommand("Stop", false);
        else fw.src='#'
        doc.body.removeChild(f);
    }, timeout);
    f.addEventListener('load', function(e){clearTimeout(to);}, true);
    f.src = src;
})(document, (window.location.protocol=='https:' ? 'https:' : 'http:')+'//dpm.zebestof.com/sync-all.html', 1500);