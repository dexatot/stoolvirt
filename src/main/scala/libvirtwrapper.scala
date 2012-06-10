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
        libVirt.getDomain(name)
    }

    class LibVirt {
        
        val conn = new Connect("lxc:///default", true)

        def getDomain(name: String) = {
            val testDomain = conn.domainLookupByName(name)
            println(testDomain.getInfo())
        }

    }

}