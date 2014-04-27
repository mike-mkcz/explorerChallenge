function whichWay() {

    $.getJSON( "whichWay", function( data )
    {
       console.log(data.direction);
    });
}

setInterval(function () { whichWay() }, 2000);