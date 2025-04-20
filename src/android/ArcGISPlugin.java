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
    return false;
  }
}

