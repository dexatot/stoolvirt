import org.libvirt._

import scala.collection.mutable._

object LibvirtWrapper {

    import Property._
    
    def createAction(parametr: List[Property], name: String) = {
        
    }

    def destroyAction(name: String) = {
        
    }

    def startAction(name: String) = {
        
    }

    def stopAction(name: String) = {
        
    }

    def pingAction(name: String) = {
        
    }

    def infoAction(name: String) = {
        val libVirt = new LibVirt()
        libVirt.getDomain()
    }

    class LibVirt {
        
        val conn = new Connect("test:///default", true)

        def getDomain() = {
            val testDomain = conn.domainLookupByName("test")
            println(testDomain.getInfo())
        }

    }

}