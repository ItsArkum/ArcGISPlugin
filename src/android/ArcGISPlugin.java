package com.github.arcgisplugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.ArcGISMap;

public class ArcGISPlugin extends CordovaPlugin {
  private MapView mapView;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackCtx) throws JSONException {
    if ("showMap".equals(action)) {
      cordova.getActivity().runOnUiThread(() -> {
        mapView = new MapView(cordova.getContext());
        ArcGISMap map = new ArcGISMap("https://www.arcgis.com"); 
        mapView.setMap(map);
        // attach mapView to your layout or return success
        callbackCtx.success();
      });
      return true;
    }
    else if ("showOfflineMap".equals(action)) {
      final String pkgPath = args.getString(0);
      cordova.getThreadPool().execute(() -> {
        try {
          MobileMapPackage mmpk = new MobileMapPackage(pkgPath);
          mmpk.loadAsync();
          mmpk.addDoneLoadingListener(() -> {
            if (mmpk.getLoadStatus() == LoadStatus.LOADED) {
              mapView = new MapView(cordova.getContext());
              mapView.setMap(mmpk.getMaps().get(0));
              callbackCtx.success();
            } else {
              callbackCtx.error("Failed to load: " + mmpk.getLoadError().getMessage());
            }
          });
        } catch (Exception e) {
          callbackCtx.error(e.getMessage());
        }
      });
      return true;
    }
    return false;
  }
}

