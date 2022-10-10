val f_column: List[String] =
  List("1", "2", "3", "4", "5", "6", "G", "B", "O", "3x", "4x", "FH", "KS", "GS", "KN", "CH", "U", "O", "E")
val builder = List.newBuilder[String]

def cells(cellWidth: Int = 3, cellNum: Int = 3, desc: String) = 
  "|" + desc + ("|" + " " * cellWidth) * (cellNum - 1) + "|" + '\n'
  
def bar(cellWidth: Int = 3, cellNum: Int = 3) = (("+" + ("-") * cellWidth) * cellNum) + "+" + '\n'

def mesh(cellWidth: Int = 3, cellNum: Int = 3):String = {
  for(s:String <- f_column)
    builder += (bar(cellWidth) + cells(cellWidth, cellNum,  s.padTo(3, ' ')))
  builder += bar(cellWidth)
  builder.toString.replace("ListBuffer(", "").replace(", ", "").replace(")", "")
}

@main def main: Unit = 
  println(mesh())