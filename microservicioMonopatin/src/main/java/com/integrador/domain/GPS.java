package com.integrador.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
public class GPS {
	 private double latitud;
	 private double longitud;

	    public GPS(double latitud, double longitud) {
	        this.latitud = latitud;
	        this.longitud = longitud;
	    }

		public GPS() {
			super();
		}

		public double getLatitud() {
			return latitud;
		}

		public double getLongitud() {
			return longitud;
		}

		public void setLatitud(double latitud) {
			this.latitud = latitud;
		}

		public void setLongitud(double longitud) {
			this.longitud = longitud;
		}
		
		
		
	    
}
