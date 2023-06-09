import { useState, useEffect, useRef } from "react";
import { useLocation } from 'react-router-dom';

import {
  GoogleMapsProvider,
  useGoogleMap,
} from "@ubilabs/google-maps-react-hooks";

import { axBase } from '@/apis/api/axiosInstance'
import { Airplanes } from '@/assets/Airplanes'

import { NavTop } from '@/components';


const mapOptions = {
  zoom: 9,
  center: {
    lat: 43.68,
    lng: -79.43,
  },
};
interface flightType {
  flight: string
  id: number
  ticketNo: string
  tmpDir: number
  tmpLat: number
  tmpLng: number
}
interface volNo {
  volunteerNo: number
}

const icons: Record<number, { icon: string }> = {
  0: {
    icon: Airplanes.Airplane_0,
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
  30: {
    icon: Airplanes.dep_Airport,
  },
  31: {
    icon: Airplanes.arr_Airport,
  },

};

function direction(dir: number): number {
  if (dir < 15) {
    return 0;
  } else if (dir < 30) {
    return 1;
  } else if (dir < 45) {
    return 2;
  } else if (dir < 60) {
    return 3;
  } else if (dir < 75) {
    return 4;
  } else if (dir < 90) {
    return 5;
  } else if (dir < 105) {
    return 6;
  } else if (dir < 120) {
    return 7;
  } else if (dir < 135) {
    return 8;
  } else if (dir < 150) {
    return 9;
  } else if (dir < 165) {
    return 10;
  } else if (dir < 180) {
    return 11;
  } else if (dir < 195) {
    return 12;
  } else if (dir < 210) {
    return 13;
  } else if (dir < 225) {
    return 14;
  } else if (dir < 240) {
    return 15;
  } else if (dir < 255) {
    return 16;
  } else if (dir < 270) {
    return 17;
  } else if (dir < 285) {
    return 18;
  } else if (dir < 300) {
    return 19;
  } else if (dir < 315) {
    return 20;
  } else if (dir < 330) {
    return 21;
  } else if (dir < 345) {
    return 22;
  } else {
    return 23;
  }
};

export default function Index() {
  const location = useLocation()
  const [volunteerNo, setVolunteerNo] = useState<number>(location.state.volunteerNo);
  const [mapContainer, setMapContainer] = useState<HTMLDivElement | null>(null);

  return (
    <GoogleMapsProvider
      googleMapsAPIKey={"AIzaSyAHs9uf8emfWMZUHVFxcCHsgCGk8iS_euM"}
      mapContainer={mapContainer}
      mapOptions={mapOptions}
    >
      <div ref={(node) => setMapContainer(node)} style={{ width: "100%", height: "100vh" }} />
      <Location volunteerNo={volunteerNo} />
    </GoogleMapsProvider>
  );
}

function Location({ volunteerNo }: volNo) {
  const lat = useRef(0);
  const lng = useRef(0);
  const dir = useRef(0);

  const depLat = useRef(0);
  const depLng = useRef(0);
  const arriveLat = useRef(0);
  const arriveLng = useRef(0);

  const map = useGoogleMap();
  const markerRef = useRef<google.maps.Marker>();
  const depAirportRef = useRef<google.maps.Marker>();
  const arriveAirportRef = useRef<google.maps.Marker>();
  const pathRefArray = useRef<[]>([]);
  pathRefArray.current = [];


  async function fetchApi() {

    // console.log("fetchApi 호출됨");
    // console.log("첫 번째 동기 통신 : 항공기 초기화 정보 호출");

    await axBase({
      method: 'get',
      url: `volunteer/gps/${volunteerNo}`,
    }
    )
      .then((res) => {
        // console.log("첫 번째 동기 :  항공기 초기화 정보 호출 then");
        const data = res.data;
        lat.current = data[0][0];
        lng.current = data[0][1];
        dir.current = data[0][2];

        // console.log("초기화 항공기 -> lat : lng : dir= " + lat.current + " : " + lng.current + " : " + dir.current);

      })
      .catch(() => {
        // console.log("첫 번째 동기 통신 에러 발생!");
        lat.current = 0;
        lng.current = 0;
        dir.current = 0;
      });

    markerRef.current = new google.maps.Marker({
      map,
      icon: icons[direction(dir.current)].icon,
      position: { lat: lat.current, lng: lng.current }
    });

    // console.log("두 번째 동기 통신 : 공항 정보 호출");
    await axBase({
      method: 'get',
      url: `volunteer/airport/${volunteerNo}`,
    })
      .then((res) => {
        // console.log("두 번째 동기 통신 : 공항 정보 호출 then");
        // console.log(res.data); 
        // console.log(res.data.depLng); 

        depLat.current = res.data.depLat;
        depLng.current = res.data.depLng;
        arriveLat.current = res.data.arriveLat;
        arriveLng.current = res.data.arriveLng;
        // console.log(" depLat.current " + depLat.current);
        // console.log(" arriveLat.current " + arriveLat.current);
      })
      .catch(() => {
        // console.log("두 번째 동기 통신 에러 발생!");
        depLat.current = 10;
        depLng.current = 0;
        arriveLat.current = 20;
        arriveLng.current = 0;
      });


    depAirportRef.current = new google.maps.Marker({
      map,
      icon: icons[30].icon,
      position: { lat: depLat.current, lng: depLng.current }
    });

    arriveAirportRef.current = new google.maps.Marker({
      map,
      icon: icons[31].icon,
      position: { lat: arriveLat.current, lng: arriveLng.current }
    });


    // console.log("세 번째 동기 통신 : 항공기 path 정보 호출");
    await axBase({
      method: 'get',
      url: `volunteer/path/${volunteerNo}`,
    })
      .then((res) => {
        // console.log("세 번째 동기 : 항공기 path 정보호출 then");
        const data = res.data;
        // console.log("res : ", res);
        // console.log("res.data : ", res.data);

        data.map((cur: flightType) => {
          // console.log(cur);
          // console.log(cur.flight)
          new google.maps.Marker({
            position: { lat: cur.tmpLat, lng: cur.tmpLng },
            map,
            icon: icons[direction(cur.tmpDir)].icon,
          });
        })
      })
      .catch((err) => console.log(err));
  }


  useEffect(() => {
    // console.log('맵:',map)
    // console.log('마카:',markerRef.current)
    if (!map || markerRef.current) return;
    // console.log('맵 랜더링 이 후')
    //맨 처음 맵 초기화 할 때, 한번 항공 위치랑 공항 마크 찍기

    //1. 항공기 gps 호출하기
    //2. 공항 호출하기
    fetchApi();


    map.panTo({ lat: lat.current, lng: lng.current });



  }, [map]
  );//end of useEffect

  setInterval(() => {
    if (location.pathname !== '/gps') return
    if (!markerRef.current || !map) return;
    if (isNaN(lat.current) || isNaN(lng.current)) return;
    // console.log('setInterval 반복 ')
          
    // const volunteerNo = 1;
    
    axBase({
      method: 'get',
      url: `volunteer/gps/${volunteerNo}`,
    })
      .then((res) => {
        const data = res.data;
        lat.current = data[0][0];
        lng.current = data[0][1];
        dir.current = data[0][2];

        // console.log("lat : lng : dir= " + lat.current + " : " + lng.current + " : " + dir.current);

      })
      .catch(() => {
        // console.log("setInterval 에러 발생!");
        lat.current = 0;
        lng.current = 0;
        dir.current = 0;
      });

    markerRef.current.setPosition({ lat: lat.current, lng: lng.current });
    markerRef.current.setIcon(icons[direction(dir.current)].icon);
    map.panTo({ lat: lat.current, lng: lng.current });

  }, 30000);
  return (<NavTop.NavBack NavContent='항공기 위치' />)
}