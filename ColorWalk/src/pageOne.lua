local composer = require( "composer" )
local touchable = require("src.Touchable")
 
local scene = composer.newScene()
 
-- -----------------------------------------------------------------------------------
-- Code outside of the scene event functions below will only be executed ONCE unless
-- the scene is removed entirely (not recycled) via "composer.removeScene()"
-- -----------------------------------------------------------------------------------
 
 
 
 
-- -----------------------------------------------------------------------------------
-- Scene event functions
-- -----------------------------------------------------------------------------------
 
-- create()
function scene:create( event )
 
    local sceneGroup = self.view
    -- Code here runs when the scene is first created but has not yet appeared on screen
 
end
 
 
-- show()
function scene:show( event )
 
  
    local sceneGroup = self.view
    local phase = event.phase
    local backgroundImage;
    local foregroundImage;
    local objects  = {};
    local level = "pages." .. event.params.levelName;
    print(level) 
    if ( phase == "will" ) then
        -- Code here runs when the scene is still off screen (but is about to come on screen)
    local page = require(level);
    print(page.background)
    if page.background ~= "none" and page.background ~= "null" then
        backgroundImage = display.newImageRect(page.background, display.actualContentWidth, display.actualContentHeight);
        backgroundImage.x = display.contentCenterX;
        backgroundImage.y = display.contentCenterY;
    end
    if(page.foreground ~= "none" and page.foreground ~= "null") then
        foregroundImage = display.newImageRect(page.foreground, display.actualContentwidth, display.actualContentHeight);
        foregroundImage.x = display.contentCenterX;
        foregroundImage.y = display.contentCenterY;
    end
    local temp = page.objects[1];
    print(temp.ID);
    print(temp.soundPath)
    --touchable:createTouchable(temp);

    --objects[1] = touchable:createTouchable(page.objects[1]);
    local temp = {};
    for i = 1, page.objectCount do --pseudocode
        objects[i] = touchable:createTouchable(page.objects[i]);
    end
    elseif ( phase == "did" ) then
        -- Code here runs when the scene is entirely on screen
 
    end

end
 
 
-- hide()
function scene:hide( event )
 
    local sceneGroup = self.view
    local phase = event.phase
 
    if ( phase == "will" ) then
        -- Code here runs when the scene is on screen (but is about to go off screen)
 
    elseif ( phase == "did" ) then
        -- Code here runs immediately after the scene goes entirely off screen
 
    end
end
 
 
-- destroy()
function scene:destroy( event )
 
    local sceneGroup = self.view
    -- Code here runs prior to the removal of scene's view
 
end
 
 
-- -----------------------------------------------------------------------------------
-- Scene event function listeners
-- -----------------------------------------------------------------------------------
scene:addEventListener( "create", scene )
scene:addEventListener( "show", scene )
scene:addEventListener( "hide", scene )
scene:addEventListener( "destroy", scene )
-- -----------------------------------------------------------------------------------
 
return scene