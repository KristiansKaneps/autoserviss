import React from 'react';
import {createRoot} from 'react-dom/client';
import Home from '../components/Home';

const appRootElement = document.getElementById('app');
if (appRootElement !== null) {
    const appRoot = createRoot(appRootElement);
    const component = Home;
    appRoot.render(React.createElement(component));
}

window.onload = function() {
    const header = document.querySelector('header')!;
    const headerHeight = header.getBoundingClientRect().height;

    const breakpoints: number[] = [];
    const breakpointTheme: string[] = [];
    const main = document.querySelector('main')!;
    breakpoints.push(main.offsetTop);
    breakpointTheme.push('light');
    const mainDivList = document.querySelectorAll('div.main-section');
    let i = 1;
    let expectedModResult = 1;
    for (const el of mainDivList) {
        const mainDiv = el as HTMLDivElement;
        const headerAttr = mainDiv.getAttribute('data-header');
        breakpoints.push(mainDiv.offsetTop);
        if (headerAttr === null) {
            breakpointTheme.push(i++ % 2 === expectedModResult ? 'dark' : 'light');
        } else {
            breakpointTheme.push(headerAttr);
            if ((i % 2 === 1 && headerAttr === 'light') || (i % 2 === 0 && headerAttr === 'dark'))
                expectedModResult = 0;
            else
                expectedModResult = 1;
            i++;
        }
    }

    const classNameSuffix = !header.className.length ? 'dark' : ' dark';
    let prevClassName: string | null = null;

    function handleScrollEvent() {
        let i = breakpoints.length - 1;
        // noinspection StatementWithEmptyBodyJS
        for (; i > 0 && !(breakpoints[i]! - headerHeight < window.scrollY); i--);
        const theme = breakpointTheme[i]!;
        if (theme === 'dark') {
            if (prevClassName === null) {
                prevClassName = header.className;
                header.className += classNameSuffix;
            }
        } else {
            if (prevClassName !== null) {
                header.className = prevClassName;
                prevClassName = null;
            }
        }
    }

    window.addEventListener('scroll', handleScrollEvent);
    handleScrollEvent();
};
