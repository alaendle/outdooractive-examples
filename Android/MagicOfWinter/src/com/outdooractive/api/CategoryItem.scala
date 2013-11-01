package com.outdooractive.api;

import scala.util.parsing.json.JSON
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.JavaConversions.seqAsJavaList
import play.api.libs.json.Json
import android.util.Log

class CategoryItem(x: Map[String, Any]) {
  val json = Json.parse("{\"tour\":[{\"lineOptions\":{\"strokeColor\":\"#ff0000\"},\"time\":{\"min\":0},\"length\":221.1681419763324,\"elevation\":{\"ascent\":25,\"descent\":0,\"minAltitude\":863,\"maxAltitude\":888},\"rating\":{\"condition\":2,\"difficulty\":1,\"technique\":2,\"qualityOfExperience\":5,\"landscape\":5,\"qualityLevel\":2,\"communityRating\":0.0,\"communityRatingCount\":0},\"labels\":{\"top\":true},\"startingPoint\":{\"lon\":10.560643005371968,\"lat\":47.56562400702847},\"season\":{\"jan\":true,\"feb\":true,\"mar\":true,\"apr\":false,\"may\":false,\"jun\":false,\"jul\":false,\"aug\":false,\"sep\":false,\"oct\":false,\"nov\":false,\"dec\":true},\"publicTransit\":\"Bahnlinie Pfronten, Bus ÖPNV, Pfronten Mobil\",\"startingPointDescr\":\"Skizentrum Pfronten-Steinach\",\"gettingThere\":\"B 309\",\"parking\":\"Skizentrum\",\"properties\":{\"property\":[{\"tag\":\"oneWayTour\",\"text\":\"Streckentour\",\"hasIcon\":false},{\"tag\":\"dining\",\"text\":\"mit Einkehrmöglichkeit\",\"hasIcon\":true,\"iconURL\":\"http://res.oastatic.com/v3/tags/dining.png\"},{\"tag\":\"suitableforfamilies\",\"text\":\"familiengerecht, Kinder\",\"hasIcon\":true,\"iconURL\":\"http://res.oastatic.com/v3/tags/suitableforfamilies.png\"}]},\"pois\":{\"poi\":[{\"id\":\"1711011\",\"linkSuggested\":true},{\"id\":\"1638671\",\"linkSuggested\":true},{\"id\":\"1619672\",\"linkSuggested\":true}]},\"elevationProfile\":{\"title\":\"large elevationprofile\",\"id\":\"3483346\"},\"difficulties\":{\"difficulty\":[{\"type\":\"Schwierigkeit\",\"value\":\"2\"}]},\"maps\":\"Pfronten Ortsplan Winter, Wintersportkarte Ostallgäu erhältlich im Haus des Gastes\",\"category\":{\"id\":\"4476266\",\"name\":\"Rodeln\",\"iconUrl\":\"http://res.oastatic.com/icons/alpstein/sledging/aspen_light/sledging.small.png\",\"iconInactiveUrl\":\"http://res.oastatic.com/icons/alpstein/sledging/silver/sledging.small.png\",\"iconMapUrl\":\"http://res.oastatic.com/icons/alpstein/sledging/aspen_light/sledging.mapscreen.png\",\"iconInactiveMapUrl\":\"http://res.oastatic.com/icons/alpstein/sledging/silver/sledging.mapscreen.png\",\"iconTransparentUrl\":\"http://res.oastatic.com/icons/alpstein/sledging/sledging.geocontent.png\",\"strokeColor\":\"#ff0000\",\"isWinter\":true},\"title\":\"Rodelbahn Skizentrum Pfronten-Steinach\",\"longText\":\"Auf der 200 m langen Winterrodelbahn direkt an den Liften haben kleine und große Leute riesigen Spass... Rodelverleih: - Lenkbobverleih in der Scheiberalm direkt im Skizentrum in Pfronten-Steinach, Tel.: 08363/926178 - Sport Kolb, Allgäuer Str. 5, 87459 Pfronten-Ried, Tel.: 08363/92130\",\"primaryImage\":{\"id\":\"1826163\"},\"geometry\":\"10.560643,47.565624 10.559990,47.565620 10.559398,47.565248 10.558883,47.565045 10.558540,47.564495\",\"images\":{\"image\":[{\"meta\":{\"date\":{\"created\":\"2011-02-21T15:42:22\",\"lastModified\":\"2012-04-03T20:13:23\",\"firstPublish\":\"2011-02-21T15:42:22\"},\"system\":{\"createdIn\":\"11\",\"lastModifiedIn\":\"11\"},\"source\":{\"logo\":{\"title\":\"Pfronten Tourismus\",\"id\":\"2062798\",\"logo\":true},\"id\":\"1008769\",\"name\":\"Pfronten Tourismus\",\"url\":\"http://www.pfronten.de/\"},\"workflow\":{\"state\":\"new\"}},\"id\":\"1826163\",\"gallery\":true}]},\"media\":{},\"regions\":{\"region\":[{\"id\":\"1040655\",\"type\":\"commune\",\"isStartRegion\":true},{\"id\":\"1022086\",\"type\":\"continent\"},{\"id\":\"1022089\",\"type\":\"country\"},{\"id\":\"1040040\",\"type\":\"district\"},{\"id\":\"1044895\",\"type\":\"province\"},{\"id\":\"1010204\",\"type\":\"province\",\"isStartRegion\":true},{\"id\":\"1010204\",\"type\":\"customarea\",\"isStartRegion\":true},{\"id\":\"1027049\",\"type\":\"tourismarea\",\"isStartRegion\":true}]},\"winterActivity\":true,\"meta\":{\"date\":{\"created\":\"2012-06-21T11:10:36\",\"lastModified\":\"2013-04-11T17:56:38\",\"firstPublish\":\"2012-06-21T11:18:40\"},\"system\":{\"createdIn\":\"11\",\"lastModifiedIn\":\"12\"},\"source\":{\"logo\":{\"title\":\"Pfronten Tourismus\",\"id\":\"2062798\",\"logo\":true},\"id\":\"1008769\",\"name\":\"Pfronten Tourismus\",\"url\":\"http://www.pfronten.de/\"},\"workflow\":{\"state\":\"published\"},\"author\":\"Pfronten Tourismus\",\"createdBy\":\"3002457\"},\"id\":\"3483331\",\"type\":\"5123\",\"frontendtype\":\"tour\",\"ranking\":55.0,\"opened\":false}]}")
  Log.i("Parsing", "Id = " + ((json \ "tour")(0) \ "id").as[String])

  def this(jsonString: String) = this(JSON.parseFull(jsonString).get.asInstanceOf[Map[String, Any]])

  val id = x.getOrElse("id", "0").asInstanceOf[String].toInt
  val name = x.getOrElse("name", "root").asInstanceOf[String]
  val children: java.util.List[CategoryItem] = for {
    M(map) <- List(x)
    L(categories) = map getOrElse ("category", Nil)
    M(category) <- categories
  } yield {
    new CategoryItem(category)
  }

  def findById(id: Int): CategoryItem = {
    if (this.id == id) {
      this
    } else {
      children.map(_.findById(id)).find(_ != null).getOrElse(null)
    }
  }

  def hasChildren: Boolean = children.size() > 0

  override def toString: String = name;

  def getChildrenIds: java.util.ArrayList[Integer] = new java.util.ArrayList[Integer](children.map(x => x.id.asInstanceOf[Integer]))

  def getChildrenNames: java.util.ArrayList[String] = new java.util.ArrayList[String](children.map(x => x.name))
}
