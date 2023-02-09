import { useState, useEffect, useRef } from "react";

import {
  GoogleMapsProvider,
  useGoogleMap,
} from "@ubilabs/google-maps-react-hooks";

// import axios from 'axios';
import { useRecoilState, useRecoilValue } from 'recoil';

import { axBase } from '@/apis/api/axiosInstance'
import { URL as ServerURL } from '@/states/Server';

const mapOptions = {
  zoom: 12,
  center: {
    lat: 43.68,
    lng: -79.43,
  },
  // mapContainerClassName : "map-container"
};

export default function Index() {
  const [mapContainer, setMapContainer] = useState<HTMLDivElement | null>(null);

  return (
    <GoogleMapsProvider
      googleMapsAPIKey={"AIzaSyAHs9uf8emfWMZUHVFxcCHsgCGk8iS_euM"}
      mapContainer={mapContainer}
      mapOptions={mapOptions}
    >
      <div ref={(node) => setMapContainer(node)} style={{width: "100%", height: "100vh" }} />
      <Location />
    </GoogleMapsProvider>
  );
}

function Location() {

  const lat = useRef<number>(0);
  const lng = useRef(0);

  const  map  = useGoogleMap();
  const markerRef = useRef<google.maps.Marker>();
  
  useEffect(() => {
    if (!map || markerRef.current) return;
    markerRef.current = new google.maps.Marker({ map });
  }, [map]);

  const URL = useRecoilValue(ServerURL);
  
  setInterval(() => {
    if (!markerRef.current || !map) return;
    if (isNaN(lat.current) || isNaN(lng.current)) return;
          
    const volunteerNo = 1;
    
    axBase({
      method: 'get',
      url: `volunteer/gps/${volunteerNo}`,
    })
    .then((res) => { 
      const data =  res.data;
      const dir = data[0][2];
      lat.current = data[0][0]; 
      lng.current = data[0][1];
      console.log("lat : lng = " + lat.current + " : " + lng.current);
      
    })
    .catch((err) => console.log(err));
  markerRef.current.setPosition({ lat: lat.current, lng: lng.current });
  map.panTo({ lat: lat.current, lng: lng.current });

  }, 5000);

  return (
    <div className="lat-lng">
      <input
        step={0.01}
        type="number"
        value={lat.current}
        onChange={(event) => lat.current=(parseFloat(event.target.value))}
      />
      <input
        step={0.01}
        type="number"
        value={lng.current}
        onChange={(event) => lng.current=(parseFloat(event.target.value))}
      />
    </div>
  );
}