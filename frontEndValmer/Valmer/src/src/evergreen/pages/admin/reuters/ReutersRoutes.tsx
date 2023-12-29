import {Routes, Route} from 'react-router-dom';
import {UmsMexCat} from './umsMexCat';
import {UmsLatam} from './umsLatam';
import {LiqLatamCat} from './liqLatamCat';

export const ReutersRoutes = () => (
    <Routes>
        <Route path='/ums/mex/cat/*' element={<UmsMexCat/>}/>
        <Route path='/ums/latam/*' element={<UmsLatam/>}/>
        <Route path='/liq/latam/cat/*' element={<LiqLatamCat/>}/>
    </Routes>
)