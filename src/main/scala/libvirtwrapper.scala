import org.libvirt._

import scala.collection.mutable._

object LibvirtWrapper {

    import Property._

    val libVirt = new LibVirt()
    
    def createAction(parametrs: List[Property], name: String) = {
        
        val rootfs = "/opt/rootfs/" + name
        var os = ""
        var net = networkXml("default")

        for( property <- parametrs ) {
            property.name match {
                case "os" => os = property.value
                case "net" => net = networkXml(property.value)
                case _ => println("error")
            }
        }
        
        libVirt.domainDefine(baseXml(os, rootfs, net).toString)

    }

    def destroyAction(name: String) = {        
        libVirt.domainDestroy(name)
    }

    def startAction(name: String) = {
        libVirt.domainCreate(name)   
    }
    
    def suspendAction(name: String) = {
        libVirt.domainSuspend(name)   
    }

    def resumeAction(name: String) = {
        libVirt.domainResume(name)       
    }
    
    def rebootAction(name: String) = {                
        libVirt.domainReboot(name)
    }

    def infoAction(name: String) = {        
        libVirt.getDomainInfo(name)
    }

    class LibVirt {
        
        val conn = new Connect("lxc:///", false)
        
        def domainReboot(name: String) = {
            val domain = conn.domainLookupByName(name)
            println(domain.destroy())
            println(domain.create())            
        }

        def getDomainInfo(name: String) = {
            val domain = conn.domainLookupByName(name)
            println(domain.getInfo())
        }

        def domainDestroy(name: String) = {
            val domain = conn.domainLookupByName(name)
            println(domain.destroy())
        }

        def domainCreate(name: String) = {
            val domain = conn.domainLookupByName(name)
            println(domain.create())
        }

        def domainDefine(xml: String) = {            
            println(conn.domainDefineXML(xml))
        }

        def domainSuspend(name: String) = {
            val domain = conn.domainLookupByName(name)
            println(domain.suspend())   
        }

        def domainResume(name: String) = {
            val domain = conn.domainLookupByName(name)
            println(domain.resume())   
        }

    }

    def defaultNetwork() = 
        <interface type='network'>
            <source network='default'/>
        </interface>  
           
    def brNetwork() = 
        <interface type='bridge'>
            <source network='br0'/> 
            <mac address='00:11:22:34:34:34'/>
        </interface>  

    def networkXml(param: String): Any = {
        param match {
            case "bridge" => brNetwork
            case "default" => defaultNetwork
            case _  => defaultNetwork
        }        
    }           

    def baseXml(name: String, rootfs: String, net: Any) = 
        <domain type='lxc'>
            <name>{name}</name>
            <memory>128000</memory>
            <os>
                <type>exe</type>
                <init>/sbin/init</init>
            </os>
            <vcpu>1</vcpu>
            <clock offset='utc'/>
            <on_poweroff>destroy</on_poweroff>
            <on_reboot>restart</on_reboot>
            <on_crash>destroy</on_crash>
            <devices>
                <emulator>/usr/lib/libvirt/libvirt_lxc</emulator>
                <filesystem type='mount'>
                    <source dir={rootfs}/>
                    <target dir='/'/>
                </filesystem>
                {net}                
                <console type='pty' />
            </devices>
        </domain>
    
}