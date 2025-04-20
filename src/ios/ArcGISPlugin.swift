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
  @objc(showOfflineMap:)
func showOfflineMap(command: CDVInvokedUrlCommand) {
  guard let path = command.arguments.first as? String else {
    sendError("Missing path", command: command)
    return
  }
  let pkg = AGSMobileMapPackage(fileURL: URL(fileURLWithPath: path))
  pkg.load { error in
    if let err = error { self.sendError(err.localizedDescription, command: command); return }
    guard let map = pkg.maps.first else {
      self.sendError("No maps", command: command); return
    }
    DispatchQueue.main.async {
      let mv = AGSMapView(frame: self.webView!.bounds)
      mv.map = map
      self.webView!.superview?.addSubview(mv)
      self.sendSuccess(command)
    }
  }
}
}