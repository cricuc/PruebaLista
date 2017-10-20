package brinkstest.com.pruebalista.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by cc_ri on 20/10/2017.
 */

public class Aplicacion {

    @SerializedName("im:name")
    public Nombre name;
    @SerializedName("im:image")
    public List<Imagen> images;
    @SerializedName("summary")
    public Descripcion summary;
    @SerializedName("category")
    public Categoria category;
    @SerializedName("im:releaseDate")
    public FechaDePublicacion releaseDate;

    public Nombre getName() {
        return name;
    }

    public List<Imagen> getImages() {
        return images;
    }

    public Descripcion getSummary() {
        return summary;
    }

    public Categoria getCategory() {
        return category;
    }

    public FechaDePublicacion getReleaseDate() {
        return releaseDate;
    }

    public void setName(Nombre name) {
        this.name = name;
    }

    public void setImages(List<Imagen> images) {
        this.images = images;
    }

    public void setSummary(Descripcion summary) {
        this.summary = summary;
    }

    public void setCategory(Categoria category) {
        this.category = category;
    }

    public void setReleaseDate(FechaDePublicacion releaseDate) {
        this.releaseDate = releaseDate;
    }

    public class Nombre{

        @SerializedName("label")
        public String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    public class Imagen{

        @SerializedName("label")
        public String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    public class Descripcion{

        @SerializedName("label")
        public String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    public class Categoria{

        @SerializedName("attributes")
        public AtributosCategoria attributes;

        public AtributosCategoria getAttributes() {
            return attributes;
        }

        public void setAttributes(AtributosCategoria attributes) {
            this.attributes = attributes;
        }
    }

    public class AtributosCategoria{

        @SerializedName("label")
        public String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    public class FechaDePublicacion{

        @SerializedName("label")
        public String label;
        public Date fechaConvertida;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Date getFechaConvertida() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            String dateInString = "2014-10-05T15:23:01Z";

            Date date = new Date();

            try {

                date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));

            } catch (ParseException e) {
                Log.i("ErrorFechaDePublicacion", e.toString());
                e.printStackTrace();
            }

            return date;
        }
    }

}
