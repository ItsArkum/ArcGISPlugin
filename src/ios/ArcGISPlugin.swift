import ArcGIS
import Cordova

@objc(ArcGISPlugin) class ArcGISPlugin: CDVPlugin {
  @objc(showMap:) func showMap(command: CDVInvokedUrlCommand) {
    DispatchQueue.main.async {
      let mapView = AGSMapView(frame: self.webView!.bounds)
      mapView.map = AGSMap(url: URL(string: "https://www.arcgis.com")!)
      self.webView!.superview?.addSubview(mapView)
      self.commandDelegate!.send(CDVPluginResult(status: .ok),
                                 callbackId: command.callbackId)
    }
  }
}