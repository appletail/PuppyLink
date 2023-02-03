package com.web.puppylink.ocr;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Value;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import com.web.puppylink.model.FlightTicket;

public class GoogleVisionApi {
	private static FlightTicket ticket = new FlightTicket();
	
	@SuppressWarnings("deprecation")
	public GoogleVisionApi(String imageFilePath) {
		try {
			String output = "";
			String array[] = new String[100];

			int ticketNoIndex = 0;
			int passengerNameIndex = 0;
			int bookingReferenceIndex = 0; 
			
			// IATA의 규정상, 3글자로 통일된다.
			int depCityIndex = 0;
			int depDateIndex = 0;
			int arriveCityIndex = 0;
			int arriveDateIndex = 0;
			int countCity = 0;
			int countDate = 0;
			int countFlight = 0;
			
			// FlightTicket에 포함되어 있지 않은 필드!
			int flightIndex = 0;
			
			Date transDepDate, transArriveDate;
		    
			List<AnnotateImageRequest> requests = new ArrayList<>();
			ByteString imgBytes = ByteString.readFrom(new FileInputStream(imageFilePath));
		
			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);
			
			try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				List<AnnotateImageResponse> responses = response.getResponsesList();
				
				for (AnnotateImageResponse res : responses) {
					if (res.hasError()) {
						System.out.printf("Error: %s\n", res.getError().getMessage());
						return;
				    }
					System.out.println("Text : ");
					output = res.getTextAnnotationsList().get(0).getDescription();
					System.out.println(output);
				
					// OCR 분석결과를 각 문장단위로 구별하면서, FlightTicket의 구성 요소에 해당하는 값들을 추출한다.
					StringTokenizer st = new StringTokenizer(output,"\n");
					for(int i=0; st.hasMoreTokens(); i++){
						array[i] = st.nextToken();
						System.out.println(i + " "+ array[i]);
						
						// CASE1. 대한항공(Korean Air)
						if(array[0].contains("KOREAN AIR")) {
							if(array[i].contains("Passenger Name")) {
								passengerNameIndex = i+1;
							}
							else if(array[i].length()==13 && array[i].matches("[0-9]*")) {
								ticketNoIndex = i;
							}
							else if(array[i].contains("Booking Reference")) {
								bookingReferenceIndex = i+1;
							}
							else if(array[i].length()==3 && !array[i].matches("[0-9]*")) {
								// 출발지는 편도 및 왕복여부와 무관하다.
								if(countCity==0) {
									depCityIndex = i;
								}
								// 편도 및 왕복 기준, 도착지 결정.
								else if(countCity==1 || countCity==2) {
									arriveCityIndex = i;
								}
								countCity++;
							}
							else if(array[i].contains("(Local Time)")) {
								// 출발시각은 편도 및 왕복여부와 무관하다.
								if(countDate==0) {
									depDateIndex = i;
								}
								// 편도 및 왕복 기준, 도착시각 결정.
								else if(countDate==1 || countDate==2) {
									arriveDateIndex = i;
								}
								countDate++;
							}
							else if(array[i].length()==6 && countFlight==0 && array[i].contains("KE")) {
								countFlight++;
								flightIndex = i;
							}
						}
					}
					ticket.setPassengerName(array[passengerNameIndex]);
					ticket.setTicketNo(array[ticketNoIndex]);
					ticket.setBookingReference(array[bookingReferenceIndex]);
					ticket.setDepCity(array[depCityIndex]);
					ticket.setDepDate(array[depDateIndex]);
					ticket.setArriveCity(array[arriveCityIndex]);
					ticket.setArriveDate(array[arriveDateIndex]);
					ticket.setFlight(array[flightIndex]);

//					System.out.println("Passenger Name : "+array[passengerNameIndex]);
//					System.out.println("Ticket Number : "+array[ticketNoIndex]);
//					System.out.println("Booking Reference : "+array[bookingReferenceIndex]);
//					System.out.println("From City : "+array[depCityIndex]);
//					System.out.println("From Date : "+array[depDateIndex]);
//					System.out.println("To City : "+array[arriveCityIndex]);
//					System.out.println("To Date : "+array[arriveDateIndex]);
//					System.out.println("Flight : "+array[flightIndex]);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public FlightTicket getFlightTicket() {
		return ticket;
	}
}
