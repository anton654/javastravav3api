package javastrava.json.impl.gson.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import javastrava.api.v3.model.StravaMapPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author Dan Shannon
 *
 */
public class MapPointSerializer implements JsonDeserializer<StravaMapPoint>, JsonSerializer<StravaMapPoint> {

	/**
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type,
	 *      com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(final StravaMapPoint point, final Type type, final JsonSerializationContext context) {
		ArrayList<Float> list = new ArrayList<Float>();
		list.add(point.getLatitude());
		list.add(point.getLongitude());
		return context.serialize(list);
	}

	/**
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type,
	 *      com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public StravaMapPoint deserialize(final JsonElement element, final Type type, final JsonDeserializationContext context)
			throws JsonParseException {
		JsonArray array = element.getAsJsonArray();
		if (array.size() < 2) {
			// empty array
			return null;
		}
		return new StravaMapPoint(array.get(0).getAsFloat(), array.get(1).getAsFloat());
	}

}
