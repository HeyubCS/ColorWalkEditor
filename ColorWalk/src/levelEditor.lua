local composer = require( "composer" )
local widget = require( "widget" )
 
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
 
    if ( phase == "will" ) then
        -- Code here runs when the scene is still off screen (but is about to come on screen)
        local widget = require( "widget" )
         
        -- The "onRowRender" function may go here (see example under "Inserting Rows", above)
         
        -- Create the widget
        local tableView = widget.newTableView(
            {
                left = 295,
                top = 0,
                height = 330,
                width = 200,
                onRowRender = onRowRender,
                onRowTouch = onRowTouch,
                listener = scrollListener
            }
        )
         
        -- Insert 40 rows
        for i = 1, 40 do
            -- Insert a row into the tableView
            tableView:insertRow{}
        end

        local function hideTableView(event)
            if(numOfTaps == 2) then
                if(tableView.x == 295) then
                    transition.to(tableView, { time=1500, alpha=0, x=(display.contentWidth - 10) })
                else
                    transition.to(tableView, { time=1500, alpha=0, x=(295) })
                end
            end
        end

        tableView:addEventListener("tap", hideTableView);
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