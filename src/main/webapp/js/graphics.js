function drawMaze()
{
    var mazeCanvas = document.getElementById("maze-canvas");
    var ctx = mazeCanvas.getContext("2d");

    //border
    ctx.beginPath();
    ctx.rect(0,0,800,600);
    ctx.stroke();
}