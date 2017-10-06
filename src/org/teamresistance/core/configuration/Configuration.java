package org.teamresistance.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

/**
 * Loads a JSON configuration file and fills out data in classes with that
 * information.
 * 
 * @author Frank McCoy
 *
 */
public class Configuration {

    private JsonObject data;

    private static Logger logger = Logger.getLogger(Configuration.class.getName());

    public Configuration(String path) {
        data = read(path);
    }

    /**
     * Instantiates and fills out an instance of type with the information stored in
     * the JSON Object named <code>token</code>.
     * 
     * @param <T>
     *            the type to be instantiated and filled out
     * @param type
     *            the type to be instantiated and filled out
     * @param token
     *            the name of the JSON object which data should be read from
     * @return a new instance of Type <code>type</code> with the data from the JSON
     *         object named <code>token</code>
     */
    public <T> T get(Class<T> type, String token) {
        if (!type.isAnnotationPresent(Configurable.class)) {
            logger.log(Level.SEVERE, "Attempted to configure class which is not Configurable");
            return null;
        }

        T t = null;
        if (!data.containsKey(token)) {
            logger.log(Level.SEVERE, "\"" + token + "\" is not in configuration file");
        } else {
            try {
                Constructor<T> ctor = type.getConstructor();
                t = (T) ctor.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if (data.get(token).getValueType() == ValueType.NULL) {
                return null;
            } else {
                parseJsonObject(type, t, data.getJsonObject(token));
            }
        }

        return t;
    }

    /**
     * Reads configuration data from JSON file
     */
    private JsonObject read(String path) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonReader reader = Json.createReader(stream);
        JsonObject result = reader.readObject();
        reader.close();
        return result;
    }

    /**
     * Recursively loads data into an Object from a given JsonObject.
     */
    private <T> void parseJsonObject(Class<T> type, Object instance, JsonObject json) {
        for (Field field : type.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Unconfigurable.class)
                    && (field.isAnnotationPresent(Configurable.class) || Modifier.isPublic(field.getModifiers()))) {
                field.setAccessible(true);
                if (field.getType().isArray()) {
                    JsonArray jsonArray = json.getJsonArray(field.getName());
                    Object array = Array.newInstance(field.getType().getComponentType(), jsonArray.size());
                    fillArray(field.getType().getComponentType(), array, jsonArray);
                    try {
                        field.set(instance, array);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        field.set(instance, getValue(json, field.getType(), field.getName()));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                field.setAccessible(false);
            }
        }
    }

    /**
     * Recursively loads data in an array from given JSON.
     */
    private <T> void fillArray(Class<T> componentType, Object array, JsonArray json) {
        for (int i = 0; i < Array.getLength(array); i++) {
            switch (componentType.getName()) {
            case "double":
                Array.set(array, i, json.getJsonNumber(i).doubleValue());
                break;
            case "int":
                Array.set(array, i, json.getJsonNumber(i).intValue());
                break;
            case "java.lang.String":
                Array.set(array, i, json.getString(i));
                break;
            case "boolean":
                Array.set(array, i, json.getBoolean(i));
                break;
            default:
                if (componentType.isArray()) {
                    JsonArray jsonArray = json.getJsonArray(i);

                    Object tmpArray = Array.newInstance(componentType.getComponentType(), jsonArray.size());
                    fillArray(componentType.getComponentType(), tmpArray, json.getJsonArray(i));
                    Array.set(array, i, tmpArray);
                } else {
                    Object tmpInstance = null;

                    Constructor<?> ctor;
                    try {
                        ctor = componentType.getConstructor();
                        tmpInstance = ctor.newInstance();
                    } catch (NoSuchMethodException | SecurityException e) {
                        e.printStackTrace();
                    } catch (InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }

                    parseJsonObject(componentType, tmpInstance, json.getJsonObject(i));

                    Array.set(array, i, tmpInstance);
                }
                break;
            }
        }

    }

    private Object getValue(JsonObject json, Class<?> type, String token) {
        if (json.isNull(token))
            return null;
        try {
            switch (type.getName()) {
            case "double":
                return json.getJsonNumber(token).doubleValue();
            case "int":
                return json.getInt(token);
            case "java.lang.String":
                return json.getString(token);
            case "boolean":
                return json.getBoolean(token);
            default:
                Object tmpInstance = null;

                Constructor<?> ctor;
                try {
                    ctor = type.getConstructor();
                    tmpInstance = ctor.newInstance();
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                } catch (InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                parseJsonObject(type, tmpInstance, json.getJsonObject(token));

                return tmpInstance;
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> JsonObjectBuilder getJsonObjectBuilder(Class<T> type, Object instance) {
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        // objBuilder.add("TypeName", this.getClass().getTypeName());
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Configurable.class)) {
                String typeName = field.getGenericType().getTypeName();
                try {
                    field.setAccessible(true);
                    if (field.get(instance) == null) {
                        objBuilder.add(field.getName(), JsonValue.NULL);
                    } else {
                        switch (typeName) {
                        case "double":
                            objBuilder.add(field.getName(), field.getDouble(instance));
                            break;
                        case "int":
                            objBuilder.add(field.getName(), field.getInt(instance));
                            break;
                        case "java.lang.String":
                            objBuilder.add(field.getName(), (String) field.get(instance));
                            break;
                        case "boolean":
                            objBuilder.add(field.getName(), field.getBoolean(instance));
                            break;
                        default:
                            objBuilder.add(field.getName(), getJsonObjectBuilder(field.getType(), field.get(instance)));
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            }
        }
        return objBuilder;
    }

}
