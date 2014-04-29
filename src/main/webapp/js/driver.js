function moveCycle()
{
    entranceCall = getEntrance();
    entranceCall.done(function(result) {
        console.log(result);
    }).fail(function() {

    });


}