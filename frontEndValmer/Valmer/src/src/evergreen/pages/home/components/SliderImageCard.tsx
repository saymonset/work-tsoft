import React from "react";
import Slider from "react-slick";

export const SliderImageCard =() =>{

    const images = [
        {
            path: "/img/boletinValmer.jpg",
            title: "Imagen 1",
        },
        {
            path: "/img/bannerDerivados.jpg",
            title: "Imagen 2",
        },
        {
            path: "/img/riesgosFinancieros.jpg",
            title: "Imagen 3",
        },
    ];


    const settings = {
        dots: true,
        infinite: true,
        speed: 1000,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 4000
    };

    const sliderItems = images.map((image) => (
        <div key={image.title}>
            <img className="mx-auto my-auto rounded-2xl"  style={{width: "500px", height: "200px"}} src={image.path} alt={image.title} />
        </div>
    ));

    return (
        <div className={`bg-gradient-to-t from-cyan-950 to-cyan-700 border-2 rounded-2xl w-full h-60 p-6`}>
            <Slider className="ml-2 mr-2" {...settings}>
                {sliderItems}
            </Slider>
        </div>
    )
}