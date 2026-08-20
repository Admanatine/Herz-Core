package net.ada.herz.core.api.data.packages;

import java.util.List;
import java.util.Map;

public record HerzPackage (String uuid,
                           String package_name,
                           List<String> description,
                           String version,
                           Map<String, String> targetPlatforms){

}
