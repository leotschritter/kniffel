package de.htwg.se.kniffel.model.dicecup

class Evaluator (strategy: EvaluateStrategy.Type[Int]){
  def getResult(data: List[Int]): Int = {
    strategy(data)
  }
}
