package com.example.demo.model;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Image {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 @Lob
	 @Column(columnDefinition="LONGBLOB")
	 private byte[] data;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "Image [id=" + id + ", data=" + Arrays.toString(data) + "]";
		}

		public Image(Long id, byte[] data) {
			super();
			this.id = id;
			this.data = data;
		}

		public Image() {
			super();
			// TODO Auto-generated constructor stub
		}

	    
	    
}
