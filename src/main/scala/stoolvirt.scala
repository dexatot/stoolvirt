import scala.util.parsing.combinator._  
import scala.util.matching.Regex  
import scala.util.parsing.input.CharSequenceReader

import scala.collection.mutable.Stack  
import scala.collection.mutable.Queue

case class Command(action: String, properties: List[Property], message: String)
case class Property(name: String, value: String)

object SToolVirt {

    class Terminal() extends RegexParsers {

        private var exit = false;

        def start() = {
            while(!exit) {
                Console.print("$ ")
                val line = Console.readLine()
                if (line.equals("Q")) 
                    exit = true
                else
                    scan(line)
            }
        }

        def scan(str: String) = {
            val input = new Queue[String]            

            val command = DefaultParser(str)

            println(command.get.action)            
        }

    }    
}

object DefaultParser extends JavaTokenParsers {

    def apply(input: String) = parseAll(command, input)

    def command = action ~ properties ~ message ^^ {
        case a ~ p ~ m => new Command(a, p, m)
    }

    val action = ident

    val message = """[\w\d-_]+""".r

    val properties = rep(property)

    val property = "-" ~ propertyName ~ propertyValue ^^ {
        case "-" ~ n ~ v => new Property(n, v)
    }

    val propertyName: Parser[String] = ident

    val propertyValue: Parser[String] = ident
}

