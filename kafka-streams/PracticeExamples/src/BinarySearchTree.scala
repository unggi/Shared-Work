import scala.collection.mutable.ArrayBuffer

class BinarySearchTree[T](rootValue: T)(comparator: (T, T) => Int) {

  case class Node[T](var left: Option[Node[T]], val value: T, var right: Option[Node[T]]) {
    var depth: Int = -1
    var nextRight: Option[Node[T]] = None
    var x = -1
    var range: Range = -1 until -1

    override def toString(): String = {
      s"[$value] => ${left.getOrElse('L')} | ${right.getOrElse('R')}"
    }
  }

  val root = Node[T](None, rootValue, None)

  private def insert(tree: Node[T], value: T): Unit = {
    comparator(tree.value, value) match {
      case 1 =>
        if (tree.right isDefined)
          insert(tree.right.get, value)
        else
          tree.right = Some(Node[T](None, value, None))
      case 0 => // do nothing since the tree already has the value
      case -1 =>
        if (tree.left isDefined)
          insert(tree.left.get, value)
        else
          tree.left = Some(Node[T](None, value, None))
    }
  }

  def build(args: T*) =
    args.foreach(insert(root, _))

  def printTree(): Unit =
    DFSInOrder {
      (node, depth) =>
        for (i <- 0 until node.depth)
          print("  ")
        println("[" + node.value.toString + "] [" + node.range + "] " + "[" + node.nextRight + "]")
    }

  def DFSInOrder(visit: (Node[T], Int) => Unit): Unit = {
    def visitor(node: Node[T], depth: Int): Unit = {
      if (node.left.isDefined)
        visitor(node.left.get, depth + 1)
      visit(node, depth)
      if (node.right.isDefined)
        visitor(node.right.get, depth + 1)
    }

    visitor(root, 0)
  }

  def DFSPostOrder(visit: (Node[T], Int) => Unit): Unit = {
    def visitor(node: Node[T], depth: Int): Unit = {
      if (node.left.isDefined)
        visitor(node.left.get, depth + 1)
      if (node.right.isDefined)
        visitor(node.right.get, depth + 1)
      visit(node, depth)
    }

    visitor(root, 0)
  }


  def linkAdjacentNodes(tree: Node[T]): ArrayBuffer[Node[T]] = {
    // walk through nodes in DFS order
    // gather all nodes into a list
    val nodes = new ArrayBuffer[Node[T]]()
    DFSInOrder {
      (node, depth) =>
        nodes.append(node)
    }
    // sort nodes, then examine adjacent pairs
    val sorted = nodes.sortBy(_.depth)
    sorted
      .iterator.sliding(2)
      .foreach {
        pair =>
          println(s"Node ${pair(0).value} => ${pair(1).value} = ${pair(0).depth == pair(1).depth}")
          if (pair(0).depth == pair(1).depth)
            pair(0).nextRight = Some(pair(1))
      }
    sorted
  }

  def renderTree(): Unit = {
    // mark all nodes with depth
    DFSInOrder {
      (node, depth) =>
        node.depth = depth
    }
    // do BFS to link adjacent nodes at the same level
    val nodes = linkAdjacentNodes(root)
    // set the x co-ordinate of each node using a first guess
    nodes
      .groupBy(_.depth)
      .foreach {
        case (depth, nodeList) =>
          var x = 0
          nodeList.foreach {
            n =>
              n.x = x
              x = x + 2
          }
      }
    DFSInOrder {
      (node, depth) =>
        val start =
          node.left match {
            case Some(left) => left.x
            case None => node.x
          }
        val end =
          node.right match {
            case Some(right) => right.x
            case None => node.x
          }
        node.range = start until end
    }

    DFSPostOrder {
      (node, depth) =>

    }


    // calculate a modified offset  by recursively checking the rightmost left node vs. leftmost right node.

    // Nodes shouldn't overlap.
    // Shift parent nodes to the right

  }
}

object BinarySearchTree {

  def main(args: Array[String]): Unit = {

    println("Building Tree")
    val tree = new BinarySearchTree[Int](5)(
      (a, b) =>
        if (a > b) 1 else if (a == b) 0 else -1
    )
    tree.build(3, 4, 1, 2, 3, 4, 5, 10, 9, 8, 7, 6)
    tree.renderTree()
    tree.printTree()
  }
}
