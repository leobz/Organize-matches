import React from 'react';
import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox';
import {PaletteTree} from './palette';
import List from "../routes/Matches/MatchList.jsx";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/List">
                <List/>
            </ComponentPreview>
            <ComponentPreview path="/List">
                <List/>
            </ComponentPreview>
        </Previews>
    );
};

export default ComponentPreviews;