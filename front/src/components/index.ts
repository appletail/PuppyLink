import BtnBsm from './Buttons/BtnBsm/BtnBsm';
import BtnLg from './Buttons/BtnLg/BtnLg';
import BtnMd from './Buttons/BtnMd/BtnMd';
import BtnSm from './Buttons/BtnSm/BtnSm';

import CardLg from './Cards/CardLg/CardLg';
import CardMd from './Cards/CardMd/CardMd';
import CardSm from './Cards/CardSm/CardSm';
import CardXL from './Cards/CardXL/CardXL';

import FooterBtn from './Footers/FooterBtn/FooterBtn';
import FooterHeart from './Footers/FooterHeart/FooterHeart';

import ImgLg from './Imgs/ImgLg/ImgLg';
import ImgSm from './Imgs/ImgSm/ImgSm';

import Input from './Inputs/Input/Input';
import InputForm from './Inputs/Input/InputForm';
import InputBtn from './Inputs/InputBtn/InputBtn';
import InputFormBtn from './Inputs/InputBtn/InputFormBtn';
import Label from './Labels/Label';

import NavBack from './navbar/Top/NavBack';
import NavCreate from './navbar/Top/NavCreate';
import NavDetail from './navbar/Top/NavDetail';
import NavLogo from './navbar/Top/NavLogo';

export const buttons = { BtnBsm, BtnSm, BtnMd, BtnLg };
export const cards = { CardSm, CardMd, CardLg, CardXL };
export const footers = { FooterBtn, FooterHeart };
export const imgs = { ImgLg, ImgSm };
export const labels = { Label };
export const inputs = { Input, InputBtn, InputForm, InputFormBtn };
export const NavTop = { NavLogo, NavCreate, NavDetail, NavBack };

export { default as NavBottom } from './navbar/bottom/NavBottom';
export { default as Detail } from './Detail/Detail';
export { default as ModalCard } from './Modal/ModalCard';
export { default as ModalForm } from './Modal/ModalForm/ModalForm';
export { default as ChannelTalk } from './ChannelTalk/ChannelService'; 