var mazeCanvasWidth = 1000;
var mazeCanvasHeight = 800;

function drawMaze(mazeDefinition)
{
    var mazeCanvas = document.getElementById("maze-canvas");

    mazeCanvas.width = mazeCanvasWidth;
    mazeCanvas.height = mazeCanvasHeight;

    var square = 20;
    var mazeWidthPx = mazeDefinition.maxXCoordinate * square;
    var mazeHeightPx = mazeDefinition.maxYCoordinate * square;
    var mazeXOffsetPx = (mazeCanvasWidth - mazeWidthPx)/2;
    var mazeYOffsetPx = (mazeCanvasHeight - mazeHeightPx)/2;


    var ctx = mazeCanvas.getContext("2d");
    ctx.clearRect(0, 0, mazeCanvasWidth, mazeCanvasHeight);

    //border
    ctx.beginPath();
    ctx.strokeStyle = "#000000";
    ctx.rect(0,0,mazeCanvasWidth, mazeCanvasHeight);
    ctx.stroke();

    for(var y=0 ; y<=mazeDefinition.maxYCoordinate ; y++)
    {
        //y origin is reversed between maze definition and canvas
        var yPositionPx = (mazeDefinition.maxYCoordinate-y)*square;

        for(var x=0 ; x<=mazeDefinition.maxXCoordinate ; x++)
        {
            if(mazeDefinition.grid[x][y])
            {
                ctx.fillStyle = "#FFFFFF";
            }
            else
            {
                ctx.fillStyle = "#000000";
            }

            ctx.beginPath();
            ctx.strokeRect(mazeXOffsetPx + x*square, mazeYOffsetPx  + yPositionPx, square, square);
            ctx.fillRect(mazeXOffsetPx + x*square, mazeYOffsetPx  + yPositionPx, square, square);
            ctx.stroke();
        }
    }
}