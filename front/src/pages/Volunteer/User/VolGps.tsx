import { useState, useEffect, useRef } from "react";

import {
  GoogleMapsProvider,
  useGoogleMap,
} from "@ubilabs/google-maps-react-hooks";

import { axBase } from '@/apis/api/axiosInstance'
import  {Airplanes} from '@/assets/Airplanes'

const mapOptions = {
  zoom: 12,
  center: {
    lat: 43.68,
    lng: -79.43,
  },
  // mapContainerClassName : "map-container"
};

const icons: Record<number, { icon: string }> = {
0: {
  icon: Airplanes.Airplane_0 ,
},
1: {
  icon: Airplanes.Airplane_1,
},
2: {
  icon: Airplanes.Airplane_2,
},
3: {
  icon: Airplanes.Airplane_3,
},
4: {
  icon: Airplanes.Airplane_4,
},
5: {
  icon: Airplanes.Airplane_5,
},
6: {
  icon: Airplanes.Airplane_6,
},
7: {
  icon: Airplanes.Airplane_7,
},
8: {
  icon: Airplanes.Airplane_8,
},
9: {
  icon: Airplanes.Airplane_9,
},
10: {
  icon: Airplanes.Airplane_10,
},
11: {
  icon: Airplanes.Airplane_11,
},
12: {
  icon: Airplanes.Airplane_12,
},
13: {
  icon: Airplanes.Airplane_13,
},
14: {
  icon: Airplanes.Airplane_14,
},
15: {
  icon: Airplanes.Airplane_15,
},
16: {
  icon: Airplanes.Airplane_16,
},
17: {
  icon: Airplanes.Airplane_17,
},
18: {
  icon: Airplanes.Airplane_18,
},
19: {
  icon: Airplanes.Airplane_19,
},
20: {
  icon: Airplanes.Airplane_20,
},
21: {
  icon: Airplanes.Airplane_21,
},
22: {
  icon: Airplanes.Airplane_22,
},
23: {
  icon: Airplanes.Airplane_23,
},

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

  const lat = useRef(0);
  const lng = useRef(0);
  const dir = useRef(0);
  const iconIdx = useRef(0);

  const  map  = useGoogleMap();
  const markerRef = useRef<google.maps.Marker>();

  
  useEffect(() => {
    if (!map || markerRef.current) return;
    markerRef.current = new google.maps.Marker({ map,
    icon : icons[0].icon });
  }, [map]);



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
      lat.current = data[0][0]; 
      lng.current = data[0][1];
      dir.current = data[0][2];
      
      console.log("lat : lng : dir= " + lat.current + " : " + lng.current + " : " + dir.current);
      
    })
    .catch((err) => console.log(err));

  if(dir.current < 15){
    iconIdx.current = 0;
  }else if(dir.current < 30){
    iconIdx.current = 1;
  }else if(dir.current < 45){
    iconIdx.current = 2;
  }else if(dir.current < 60){
    iconIdx.current = 3;
  }else if(dir.current < 75){
    iconIdx.current = 4;
  }else if(dir.current < 90){
    iconIdx.current = 5;
  }else if(dir.current < 105){
    iconIdx.current = 6;
  }else if(dir.current < 120){
    iconIdx.current = 7;
  }else if(dir.current < 135){
    iconIdx.current = 8;
  }else if(dir.current < 150){
    iconIdx.current = 9;
  }else if(dir.current < 165){
    iconIdx.current = 10;
  }else if(dir.current < 180){
    iconIdx.current = 11;
  }else if(dir.current < 195){
    iconIdx.current = 12;
  }else if(dir.current < 210){
    iconIdx.current = 13;
  }else if(dir.current < 225){
    iconIdx.current = 14;
  }else if(dir.current < 240){
    iconIdx.current = 15;
  }else if(dir.current < 255){
    iconIdx.current = 16;
  }else if(dir.current < 270){
    iconIdx.current = 17;
  }else if(dir.current < 285){
    iconIdx.current = 18;
  }else if(dir.current < 300){
    iconIdx.current = 19;
  }else if(dir.current < 315){
    iconIdx.current = 20;
  }else if(dir.current < 330){
    iconIdx.current = 21;
  }else if(dir.current < 345){
    iconIdx.current = 22;
  }else if(dir.current < 360){
    iconIdx.current = 23;
  }

  
  markerRef.current.setPosition({ lat: lat.current, lng: lng.current });
  markerRef.current.setIcon(icons[iconIdx.current].icon);
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