var scrollApi;

function initialiseLog()
{
    var settings = {
        showArrows: true
    };
    var pane = $('.scroll');
    pane.jScrollPane(settings);
    scrollApi = pane.data('jsp');
}

function updateLog(contentLine)
{
    scrollApi.getContentPane().append(contentLine + "<br/>");
    scrollApi.reinitialise();
    scrollApi.scrollToPercentY(100);
}